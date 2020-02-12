import base64
import glob
import logging
import os
import re
import shutil
import tempfile

from wic import WicError
from wic.pluginbase import SourcePlugin
from wic.misc import (exec_cmd, exec_native_cmd, get_bitbake_var)

logger = logging.getLogger('wic')

class DMVerityPlugin(SourcePlugin):
    """
    Creates dm-verity hash data for one rootfs partition, as identified by
    the --label parameter.
    """

    name = 'dm-verity'

    @classmethod
    def do_stage_partition(cls, part, source_params, creator, cr_workdir,
                           oe_builddir, bootimg_dir, kernel_dir,
                           native_sysroot):

        # We rely on the --label parameter and the naming convention
        # in partition.py prepare_rootfs() here to find the already
        # prepared rootfs partition image.
        for partition in creator._image.partitions:
            if partition.source == 'bootimg-partition':
                boot_partition = partition
                break
        else:
            raise WicError("Could not find partition with source bootimg-partition !")

        boot_partition_dir = "%s/boot.%d" % (cr_workdir, boot_partition.lineno)
        hashimg = '%s/dm-verity_%s.img' % (cr_workdir, part.label)

        if get_bitbake_var("DM_VERITY") == "true":
            pattern = '%s/rootfs_%s.*' % (cr_workdir, source_params['target_root'])
            rootfs = glob.glob(pattern)
            if len(rootfs) != 1:
                raise WicError("%s shell pattern does not match exactly one rootfs image (missing --sourceparams=\"target_root=\" parameter?): %s" % (pattern, rootfs))
            else:
                rootfs = rootfs[0]
            logger.debug("Calculating dm-verity hash for rootfs %s (native %s)." % (rootfs, native_sysroot))
            ret, out = exec_native_cmd("veritysetup format --hash=sha384 '%s' '%s'" % (rootfs, hashimg), native_sysroot)
            m = re.search(r'^Root hash:\s*(\S+)$', out, re.MULTILINE)
            if ret or not m:
                raise WicError('veritysetup failed: %s' % out)

            root_hash = m.group(1)
            data_bytes = os.stat(rootfs).st_size
            hash_bytes = os.stat(hashimg).st_size
            logger.debug("dm-verity data partition %d bytes, hash partition %d bytes, ratio %f." % (data_bytes, hash_bytes, data_bytes/hash_bytes))
            part.size = hash_bytes // 1024
            part.source_file = hashimg

            # Step 1/3: Append 'DMVerity' in hex. IMPORTANT: "Image" was the
            # name for the kernel that was specified in machine's conf.

            ret, out = exec_native_cmd("echo -n -e '\x44\x4d\x56\x65\x72\x69\x74\x79' >> %s/Image" % (boot_partition_dir), native_sysroot)
            if ret:
                raise WicError('Append DMVerity failed: %s' % out)

            # Step 2/3: Append root_hash in hex.
            root_hash_in_hex = r'\x' + r'\x'.join(a+b for a,b in zip(root_hash[::2], root_hash[1::2]))
            ret, out = exec_native_cmd("echo -n -e '%s' >> %s/Image" %(root_hash_in_hex, boot_partition_dir), native_sysroot)
            if ret:
                raise WicError('Append roothash failed: %s' % out)

            # Step 3/3: Append padding bytes for 64-byte alignment.
            ret, out = exec_native_cmd("dd if=/dev/zero bs=1 count=8 >> %s/Image" % (boot_partition_dir), native_sysroot)
            if ret:
                raise WicError('Append padding bytes failed: %s' % out)

            logger.debug("Appending bytes to kernel completed.")

        else:
                exec_native_cmd("dd if=/dev/zero bs=1 count=1 >> %s" % hashimg, native_sysroot)

        # Set the kernel image path in the .its file for use with mkimage later.
        shutil.copyfile("%s" % (get_bitbake_var("ITS_FILE")), "%s/temp_kmb_os_image.its" % (cr_workdir))
        out = exec_cmd("sed -i s:Image:%s/Image: %s/temp_kmb_os_image.its" % (boot_partition_dir, cr_workdir)) # ":" as delimiter is safer

        # Sign the kernel with keys in KEYS_DIR to generate a signed FIT image.
        ret, out = exec_native_cmd("uboot-mkimage -f %s/temp_kmb_os_image.its -k %s sign-fit-img.itb" % (cr_workdir, get_bitbake_var("KEYS_DIR")), native_sysroot)
        if ret:
            raise WicError('Create FIT image failed: %s' % out)
        else:
            logger.debug("Create FIT image SUCCEEDED: %s" % out)

        # Copy the signed FIT image to the boot partition.
        shutil.copyfile("sign-fit-img.itb", "%s/Image" % boot_partition_dir)

        # Re-prepare boot partition to include the signed FIT image.
        logger.debug('Re-prepare boot partition using rootfs in %s', boot_partition_dir)
        boot_partition.prepare_rootfs(cr_workdir, oe_builddir, boot_partition_dir, native_sysroot, False)

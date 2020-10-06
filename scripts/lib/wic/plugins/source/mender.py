import re
import logging
import os
import shutil
import subprocess
import time
import datetime
from distutils.dir_util import copy_tree

from wic import WicError
from wic.engine import get_custom_config
from wic.pluginbase import SourcePlugin
from wic.misc import (exec_cmd, exec_native_cmd,
                      get_bitbake_var, BOOTDD_EXTRA_SPACE)

logger = logging.getLogger('wic')
#BootimgEFIPlugin = importlib.__import__(name = "wic.plugins.source.bootimg-efi", fromlist = ['BootimgEFIPlugin'], level = 0).BootimgEFIPlugin

class menderio_boot(SourcePlugin):
#
    name = "mender-io"

    @staticmethod
    def do_sign_pe(key, crt, input, output):
        cmd = "sbsign --key %s --cert %s --output %s %s" % (key, crt, output, input)
        exec_cmd(cmd)

    @classmethod
    def gen_grub_image(cls, workdir, source_params):
        """
        Generate grub efi program, pull in modules
        """

        grub_modules = "all_video boot blscfg btrfs cat configfile cryptodisk echo efi_netfs efifwsetup efinet ext2"
        grub_modules += " fat font gcry_rijndael gcry_rsa gcry_serpent gcry_sha256 gcry_twofish gcry_whirlpool gfxmenu gfxterm gzio halt hfsplus http increment iso9660 jpeg loadenv loopback linux lvm lsefi lsefimmap"
        grub_modules += " mdraid09 mdraid1x minicmd net normal part_apple part_msdos part_gpt password_pbkdf2 png reboot search search_fs_uuid search_fs_file"
        grub_modules += " search_label serial sleep syslinuxcfg test tftp version video xfs zstd"
        grub_modules += " backtrace chain usb usbserial_common usbserial_pl2303 usbserial_ftdi usbserial_usbdebug"
        grub_modules += " hashsum"
        grub_modules += " cpio newc f2fs squash4 efienv memdisk eval"
        # Removed in 2.04
        # grub_modules += " verify"
        # New in 2.04
        grub_modules += " shim_lock tpm"

        libdir = get_bitbake_var("STAGING_LIBDIR")
        if not libdir:
            raise WicError("STAGING_LIBDIR unset!")

        mkimage_cmd = "grub-mkimage -m %s/grub.cpio -p (memdisk)/EFI/BOOT -d %s/grub/x86_64-efi -O x86_64-efi -o %s/grub-efi-bootx64.efi %s" % (workdir, libdir, workdir, grub_modules)
        exec_cmd(mkimage_cmd)

    @classmethod
    def do_copy_shim(cls, deploydir, workdir, hdddir, source_params):
        """
        Copy Shim/MokManager over to hdd
        """

        cls.do_sign_pe(source_params['db_key'], source_params['db_cert'], "%s/shim/shimx64.efi" % deploydir, "%s/EFI/BOOT/bootx64.efi" % hdddir)
        cls.do_sign_pe(source_params['db_key'], source_params['db_cert'], "%s/shim/mmx64.efi" % deploydir, "%s/EFI/BOOT/mmx64.efi" % hdddir)

    @classmethod
    def do_copy_grub(cls, deploydir, cr_workdir, hdddir, creator, source_params):
        """
        Copy grub binary over to hdd
        """
        cls.do_grub_cfg(hdddir, creator, cr_workdir, source_params)
        cls.gen_grub_image(cr_workdir, source_params)
        cls.do_sign_pe(source_params['mok_key'], source_params['mok_cert'], "%s/grub-efi-bootx64.efi" % cr_workdir, "%s/EFI/BOOT/grubx64.efi" % hdddir)

    @classmethod
    def do_copy_fwupdate(cls, deploydir, workdir, hdddir, source_params):
        """
        Copy fwupdate binary over to hdd
        """

        # yes, 2 efi/
        fwupdate_src = "%s/fwupdate-boot/boot/efi/EFI/BOOT/fwupx64.efi" % (deploydir)
        fwupdate_dst = "%s/EFI/BOOT" % (hdddir)
        os.makedirs(fwupdate_dst, exist_ok=True)
        cls.do_sign_pe(source_params['db_key'], source_params['db_cert'], fwupdate_src, "%s/fwupx64.efi" % fwupdate_dst)
        os.makedirs("%s/EFI/BOOT/FW" % hdddir)

    @classmethod
    def do_copy_ptcm(cls, deploydir, workdir, hdddir, source_params):
        """
        Copy ptcm binary over to hdd
        """
        if 'ptcm_install' not in source_params:
            raise WicError("ptcm_install is unset!")

        if (source_params['ptcm_install'] == "1"):
            ptcm_dst_dir = "%s/EFI/BOOT" % (hdddir)
            ptcm_boot = "%s/ptcm/ptcm_boot.efi" % (deploydir)
            cls.do_sign_pe(source_params['db_key'], source_params['db_cert'], ptcm_boot, "%s/ptcm/ptcm_boot_signed.efi" % deploydir)
            shutil.copyfile("%s/ptcm/ptcm_boot_signed.efi" % deploydir, "%s/ptcm_boot.efi" % ptcm_dst_dir)
            ptcm_rtdrv = "%s/ptcm/ptcm_rtdrv.efi" % (deploydir)
            cls.do_sign_pe(source_params['db_key'], source_params['db_cert'], ptcm_rtdrv, "%s/ptcm/ptcm_rtdrv_signed.efi" % deploydir)
            shutil.copyfile("%s/ptcm/ptcm_rtdrv_signed.efi" % deploydir, "%s/ptcm_rtdrv.efi" % ptcm_dst_dir)

    @classmethod
    def do_grub_cfg(cls, hdddir, creator, cr_workdir, source_params):
        """
        Prepare grub.cfg
        """

        bootloader = creator.ks.bootloader

        # search for partitions
        grubefi_conf = "search -l efi --no-floppy --set root\nset prefix=($root)/EFI/BOOT\n"
        grubefi_conf += "search -l primary --no-floppy --set rootfs2\n"
        grubefi_conf += "search -l secondary --no-floppy --set rootfs3\n"

        # Partition UUIDs
        partnum = 1
        for part in creator.parts:
            grubefi_conf += "disk_partuuid%s=\"%s\"\n" % (partnum, part.uuid)
            partnum += 1

        # default settings
        grubefi_conf += "serial --unit=0 --speed=115200 --word=8 --parity=no --stop=1\n"
        grubefi_conf += "default=boot\n"
        grubefi_conf += "timeout=%s\n" % bootloader.timeout

        # mender assumptions
        grubefi_conf += "mender_efi_uuid=${disk_partuuid1}\n"
        grubefi_conf += "mender_rootfsa_part=2\n"
        grubefi_conf += "mender_rootfsb_part=3\n"
        grubefi_conf += "mender_rootfsa_uuid=${disk_partuuid2}\n"
        grubefi_conf += "mender_rootfsb_uuid=${disk_partuuid3}\n"
        grubefi_conf += "mender_data_uuid=${disk_partuuid4}\n"

        # mender settings
        grubefi_conf += """
function maybe_pause {
    # By default we do nothing. debug-pause PACKAGECONFIG replaces this so we
    # can pause at strategic places.
    echo
}

MENDER_ENV1=${prefix}/mender_grubenv1/env
MENDER_ENVPREFIX1=${prefix}/mender_grubenv1/
MENDER_LOCK1=${prefix}/mender_grubenv1/lock
MENDER_LOCKSUM1=${prefix}/mender_grubenv1/lock.sha256sum
MENDER_ENV2=${prefix}/mender_grubenv2/env
MENDER_ENVPREFIX2=${prefix}/mender_grubenv2/
MENDER_LOCK2=${prefix}/mender_grubenv2/lock
MENDER_LOCKSUM2=${prefix}/mender_grubenv2/lock.sha256sum

function mender_check_and_restore_env {
    if ! hashsum --hash sha256 --prefix ${MENDER_ENVPREFIX2} --check ${MENDER_LOCKSUM2}; then
        load_env --skip-sig --file ${MENDER_ENV1} bootcount mender_boot_part upgrade_available
        save_env --file ${MENDER_ENV2} bootcount mender_boot_part upgrade_available
        editing=0
        save_env --file ${MENDER_LOCK2} editing
        if ! hashsum --hash sha256 --prefix ${MENDER_ENVPREFIX2} --check ${MENDER_LOCKSUM2}; then
            echo "Environment 2 still corrupt after attempted restore. Halting."
            halt
        fi
    elif ! hashsum --hash sha256 --prefix ${MENDER_ENVPREFIX1} --check ${MENDER_LOCKSUM1}; then
        load_env --skip-sig --file ${MENDER_ENV2} bootcount mender_boot_part upgrade_available
        save_env --file ${MENDER_ENV1} bootcount mender_boot_part upgrade_available
        editing=0
        save_env --file ${MENDER_LOCK1} editing
        if ! hashsum --hash sha256 --prefix ${MENDER_ENVPREFIX1} --check ${MENDER_LOCKSUM1}; then
            echo "Environment 1 still corrupt after attempted restore. Halting."
            halt
        fi
    fi
}

function mender_save_env {
    # Save redundant environment.
    editing=1
    save_env --file ${MENDER_LOCK2} editing
    save_env --file ${MENDER_ENV2} bootcount mender_boot_part upgrade_available
    editing=0
    save_env --file ${MENDER_LOCK2} editing

    editing=1
    save_env --file ${MENDER_LOCK1} editing
    save_env --file ${MENDER_ENV1} bootcount mender_boot_part upgrade_available
    editing=0
    save_env --file ${MENDER_LOCK1} editing
}

function mender_check_grubenv_valid {
    if [ "${mender_boot_part}" != "${mender_rootfsa_part}" -a "${mender_boot_part}" != "${mender_rootfsb_part}" ]; then
        return 1
    fi

    if [ "${bootcount}" != "0" -a "${bootcount}" != "1" ]; then
        return 1
    fi

    if [ "${upgrade_available}" != "0" -a "${upgrade_available}" != "1" ]; then
        return 1
    fi

    return 0
}
"""

        # mender setup
        grubefi_conf += """
mender_check_and_restore_env

# Now load environment.

# Skipping signatures?? Yes, because these values will change over time, so they
# cannot be signed. There is also no checksum facility that will work for
# changing values. Instead we check their content for validity.
load_env --skip-sig --file ${MENDER_ENV1} bootcount mender_boot_part upgrade_available

if ! mender_check_grubenv_valid; then
    if [ "${check_signatures}" == "enforce" ]; then
        echo "Unverified environment and signatures enabled. Halting."
        halt
    fi
fi

if [ "${upgrade_available}" = "1" ]; then
    if [ "${bootcount}" != "0" ]; then
        echo "Rolling back..."
        if [ "${mender_boot_part}" = "${mender_rootfsa_part}" ]; then
            mender_boot_part="${mender_rootfsb_part}"
        else
            mender_boot_part="${mender_rootfsa_part}"
        fi
        upgrade_available=0
        bootcount=0
    else
        echo "Booting new update..."
        bootcount=1
    fi

    mender_save_env
fi

    if [ "${mender_boot_part}" = "${mender_rootfsa_part}" ]; then
        mender_root="PARTLABEL=primary"
    elif [ "${mender_boot_part}" = "${mender_rootfsb_part}" ]; then
        mender_root="PARTLABEL=secondary"
    fi
    mender_info=""
    eval kernelroot=('${'rootfs${mender_boot_part}'}')
"""

        # build date
        build_date = "echo 'Image built on %u (%s)'\n" % (time.time(), datetime.datetime.utcnow())
        # menu entries
        grubefi_conf += "menuentry 'boot %s' {\n" % source_params['boot_name']
        # directly get the "$APPEND" variable from get_bitbake_var("APPEND") because there is concatenate
	# strings set in APPEND on bootloader.append from OE-Core which causing the string duplication.
        kernel = "bzImage-kernel"
        kernels = source_params['extra_kernels']
        initrd = source_params['initrd']
        var_append = get_bitbake_var("APPEND")

        grubefi_conf += "  echo Using ${kernelroot}\n"
        grubefi_conf += "  echo 'Loading kernel %s'\n" % kernel
        grubefi_conf += "  linuxefi ${kernelroot}/boot/%s root=${mender_root} ${mender_info} %s\n" \
            % (kernel, var_append)

        if initrd:
            grubefi_conf += "  echo 'Loading initial ramdisk %s'\n" % initrd
            grubefi_conf += "  initrdefi ${kernelroot}/boot/%s\n" % initrd
            grubefi_conf += build_date

        grubefi_conf += "}\n"

        if kernels:
            for k in re.compile("\s+").split(kernels.strip()):
                grubefi_conf += "menuentry 'boot %s' {\n" % k
                grubefi_conf += "  echo Using ${kernelroot}\n"
                grubefi_conf += "  echo 'Loading kernel %s'\n" % k
                grubefi_conf += "  linuxefi ${kernelroot}/boot/bzImage-%s root=${mender_root} ${mender_info} %s\n" \
                    % (k, var_append)
                if 'initrd-' + k in source_params:
                    initrd_spec = source_params['initrd-' + k]
                else:
                    initrd_spec = initrd
                if initrd_spec:
                    grubefi_conf += "  echo 'Loading initial ramdisk %s'\n" % initrd_spec
                    grubefi_conf += "  initrdefi ${kernelroot}/boot/%s\n" % initrd_spec
                    grubefi_conf += build_date
                grubefi_conf += "}\n"

        # fwsetup
        grubefi_conf += "menuentry 'Firmware Setup' {\n"
        grubefi_conf += "  echo 'Rebooting into setup'\n"
        grubefi_conf += "  fwsetup\n"
        grubefi_conf += "}\n"

        os.makedirs("%s/cpio/EFI/BOOT" % cr_workdir)
        cfg = open("%s/cpio/EFI/BOOT/grub.cfg" % cr_workdir, "w")
        cfg.write(grubefi_conf)
        cfg.close()

        # for embedding into grub image
        cpiofile = open("%s/grub.cpio" % cr_workdir, "wb")
        cpio = subprocess.Popen(["cpio", "-o", "-H", "newc", "-D", "%s/cpio" % cr_workdir], stdin=subprocess.PIPE, stdout=cpiofile)
        cpio.communicate(b'.\n./EFI/BOOT/grub.cfg\n')
        cpio.wait()
        cpiofile.close()
        if cpio.returncode:
            raise WicError("cpio died with %d" % cpio.returncode)

    @classmethod
    def do_copy_mender_env(self, deploydir, cr_workdir, hdddir, source_params):
        """
        Copy mender grub env files
        """

        if not 'mender_grub_env' in source_params:
            raise WicError("mender_grub_env is unset!")

        if not source_params['mender_grub_env']:
            raise WicError("bad mender_grub_env!")

        copy_tree("%s/grub-mender-grubenv/%s/EFI" % (deploydir, source_params['mender_grub_env']),
            "%s/EFI" % hdddir)



    @classmethod
    def do_configure_partition(cls, part, source_params, creator, cr_workdir,
                               oe_builddir, bootimg_dir, kernel_dir,
                               native_sysroot):
        """
        Called before do_prepare_partition(), creates loader-specific config
        """
        hdddir = "%s/hdd/boot" % cr_workdir

        os.makedirs("%s/EFI/BOOT" % hdddir)

        try:
            if source_params['loader'] != 'grub-efi':
                raise WicError("unrecognized %s loader: %s" % (cls.name % source_params['loader']))
        except KeyError:
            raise WicError("%s requires a loader, none specified" % cls.name)

        deploydir = get_bitbake_var("DEPLOY_DIR_IMAGE")

        if not deploydir:
            raise WicError("DEPLOY_DIR_IMAGE is unset!")

        if (not 'db_key' in source_params) or (not 'db_cert' in source_params):
            raise WicError('shim db_key/db_cert unset!')

        # TODO: verify initramfs image checksum
        # TODO: verify grub.cfg checksum
        cls.do_copy_shim(deploydir, cr_workdir, hdddir, source_params)
        cls.do_copy_mender_env(deploydir, cr_workdir, hdddir, source_params)
        cls.do_copy_grub(deploydir, cr_workdir, hdddir, creator, source_params)
        cls.do_copy_fwupdate(deploydir, cr_workdir, hdddir, source_params)
        cls.do_copy_ptcm(deploydir, cr_workdir, hdddir, source_params)

        du_cmd = "du -bks %s" % hdddir
        out = exec_cmd(du_cmd)
        blocks = int(out.split()[0])

        extra_blocks = part.get_extra_block_count(blocks)

        if extra_blocks < BOOTDD_EXTRA_SPACE:
            extra_blocks = BOOTDD_EXTRA_SPACE

        blocks += extra_blocks

        logger.debug("Added %d extra blocks to %s to get to %d total blocks",
                     extra_blocks, part.mountpoint, blocks)

        getSize = source_params['WKS_BOOT_SIZE_MB']
        if getSize:
            logger.debug("Overriding block count with WKS_BOOT_SIZE_MB value")
            blocks = 1024 * int(getSize)

        # dosfs image, created by mkdosfs
        bootimg = "%s/boot.img" % cr_workdir

        dosfs_cmd = "mkdosfs -n efi -i %s -C %s %d" % \
                    (part.fsuuid, bootimg, blocks)
        exec_native_cmd(dosfs_cmd, native_sysroot)

        mcopy_cmd = "mcopy -i %s -s %s/* ::/" % (bootimg, hdddir)
        exec_native_cmd(mcopy_cmd, native_sysroot)

        chmod_cmd = "chmod 644 %s" % bootimg
        exec_cmd(chmod_cmd)

        du_cmd = "du -Lbks %s" % bootimg
        out = exec_cmd(du_cmd)
        bootimg_size = out.split()[0]

        part.size = int(bootimg_size)
        part.source_file = bootimg


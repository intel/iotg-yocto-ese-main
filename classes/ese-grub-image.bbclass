inherit gnu-efi

do_rootfs[depends] += "grub-native:do_populate_sysroot grub-efi:do_populate_sysroot cpio-native:do_populate_sysroot"

do_ese_grub_embed_cfg(){
  # force using non-shell echo
  env echo -ne ".\n.${EFIDIR}/grub.cfg\n" | cpio -H newc -D "${IMAGE_ROOTFS}/${EFI_PREFIX}" -o > "${WORKDIR}/grub.cpio"
  rm -f "${IMAGE_ROOTFS}/${EFI_FILES_PATH}/grub.cfg"
}

python fakeroot do_ese_grub_mkimage(){
  import subprocess

  rootfs = d.getVar("IMAGE_ROOTFS")
  libdir = d.getVar("STAGING_LIBDIR")
  workdir = d.getVar("WORKDIR")
  suffix = d.getVar('GNU_EFI_ARCH')
  target = d.getVar('TARGET_ARCH')
  efp = d.getVar('EFI_FILES_PATH')
  efd = d.getVar('EFIDIR')

  grub_modules = ['all_video', 'boot', 'blscfg', 'btrfs', 'cat', 'configfile', 'cryptodisk', \
    'echo', 'efi_netfs', 'efifwsetup', 'efinet', 'ext2', 'fat', 'font', 'gcry_rijndael', \
    'gcry_rsa', 'gcry_serpent', 'gcry_sha256', 'gcry_twofish', 'gcry_whirlpool', 'gfxmenu', \
    'gfxterm', 'gzio', 'halt', 'hfsplus', 'http', 'increment', 'iso9660', 'jpeg', 'loadenv', \
    'loopback', 'linux', 'lvm', 'lsefi', 'lsefimmap', 'mdraid09', 'mdraid1x', 'minicmd', \
    'net', 'normal', 'part_apple', 'part_msdos', 'part_gpt', 'password_pbkdf2', \
    'png', 'reboot', 'search', 'search_fs_uuid', 'search_fs_file', 'search_label', 'serial', \
    'sleep', 'syslinuxcfg', 'test', 'tftp', 'version', 'video', 'xfs', 'zstd', 'backtrace', \
    'chain', 'usb', 'usbserial_common', 'usbserial_pl2303', 'usbserial_ftdi', 'xzio', 'lzopio', \
    'usbserial_usbdebug', 'hashsum', 'cpio', 'newc', 'f2fs', 'squash4', 'efienv', 'memdisk', \
    'eval', 'tpm', 'regexp' ]

  grub_cpio = ['']
  grub_input = ['-d', '%s/grub/%s-efi' % (libdir, target)]
  grub_format = ['-O', '%s-efi' % target]
  grub_output = ['-o', '%s/%s/grub%s.efi' % (rootfs, efp, suffix)]

  if d.getVar('ESE_EMBED_GRUB_CFG') == '1':
    grub_cpio = ['-m', '%s/grub.cpio' % workdir, '-p', '(memdisk)' + efd]
    bb.build.exec_func('do_ese_grub_embed_cfg', d)
  else:
    grub_cpio = ['-p', efd]

  mkimage = ['grub-mkimage']
  deploydir = d.getVar('DEPLOY_DIR_IMAGE')
  sbat = '%s/grub-efi.sbat.csv' % deploydir
  if os.path.isfile(sbat):
    mkimage += ['--sbat', sbat]

  subprocess.run(args=mkimage + grub_cpio + grub_input + grub_format + grub_output + grub_modules, check=True)
}

python(){
    d.appendVar('ESE_IMAGE_CALLS', ' do_ese_grub_mkimage;')
}

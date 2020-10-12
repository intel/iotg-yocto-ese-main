fakeroot python do_ese_efi_finalize(){
  import shutil
  rootfs = d.getVar('IMAGE_ROOTFS')
  prefix = d.getVar('EFI_PREFIX')
  suffix = d.getVar('GNU_EFI_ARCH')
  bootdir = os.path.join(rootfs + prefix, 'EFI/BOOT')

  # based on priority
  loaders = [ 'shim%s.efi' % suffix, 'grub%s.efi' % suffix, 'systemd-boot%s.efi' % suffix , None]
  target = os.path.join(bootdir, 'boot%s.efi' % suffix)

  for l in loaders:
    if l is None:
      bb.warn('ese-efi-finalize: unknown EFI loader, please update bbclass!')
      break
    source = os.path.join(bootdir, l)
    if os.path.exists(source):
      if os.path.exists(target):
        os.remove(target)
      shutil.move(source, target)
      break
}
do_ese_efi_finalize[fakeroot] = '1'

python(){
    d.appendVar('IMAGE_PREPROCESS_COMMAND', ' do_ese_efi_finalize;')
}

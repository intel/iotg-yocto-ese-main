DESCRIPTION = "Grub boot configuration file to support mender updates"
SRC_URI = "file://${BPN}.header"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit allarch grub-efi-cfg
require conf/image-uefi.conf

# no external initrd support yet

def make_menuentry(d, fd, name, image, cmdline, initrd = None, prefix = ''):
  fd.write(prefix + 'menuentry \'%s\' {\n' % name)
  fd.write(prefix + '  echo Using ${kernelroot}\n')
  fd.write(prefix + '  echo \'Loading kernel %s\'\n' % image)
  fd.write(prefix + '  linuxefi ${kernelroot}/boot/%s %s\n' % (image, cmdline))
  if initrd is not None:
    fd.write(prefix + '  echo \'Loading initrd %s\'\n' % initrd)
    fd.write(prefix + '  initrdefi ${kernelroot}/boot/%s\n' % initrd)
  fd.write(prefix + '}\n')

def make_fwsetup(d, fd):
  fd.write('menuentry \'Firmware Setup\' {\n')
  fd.write('  echo \'Rebooting into setup\'\n')
  fd.write('  fwsetup\n')
  fd.write('}\n')

python do_compile(){
  wd = d.getVar('WORKDIR')
  bpn = d.getVar('BPN')
  cmdline = d.getVar('APPEND')
  common_cmd = 'root=${mender_root} ' + cmdline
  image_type = d.getVar('KERNEL_IMAGETYPE')

  with open(os.path.join(wd, bpn + '.conf.sample'), 'w') as grubconf:

    ### from grub-efi-cfg for compatibility
    grubconf.write('# Automatically created by %s\n' % bpn)
    opts = d.getVar('GRUB_OPTS')
    if opts:
      for opt in opts.split(';'):
        grubconf.write('%s\n' % opt)

    timeout = d.getVar('GRUB_TIMEOUT')
    if timeout:
      grubconf.write('timeout=%s\n' % timeout)
    else:
      grubconf.write('timeout=50\n')
    ###

    with open(os.path.join(wd, bpn + '.header')) as header:
      grubconf.write(header.read())
    kernel = d.getVar('PREFERRED_PROVIDER_virtual/kernel')
    grubconf.write('\nset default="boot %s"\n' % (kernel))
    make_menuentry(d, grubconf, 'boot ' + kernel, image_type + '-kernel', common_cmd, None)

    for entry in sorted(d.getVarFlags('MENDER_GRUBCONF_KERNELS_MENU')):
      menu_names = d.getVarFlag('MENDER_GRUBCONF_KERNELS_MENU', entry)
      menu_name = sorted(menu_names.strip().split())
      menu_cmdlines = len(menu_name)
      spacing = ''
      if menu_cmdlines > 1:
        grubconf.write('submenu \'boot %s (menu)\' {\n' % entry)
        spacing = '  '
      for name in menu_name:
        k_cmdline = d.getVarFlag('MENDER_GRUBCONF_KERNELS', name)
        specific_cmdline = common_cmd
        if k_cmdline is not None:
          specific_cmdline += ' %s' % k_cmdline
        make_menuentry(d, grubconf, 'boot ' + name, image_type + '-' + entry + '-kernel', specific_cmdline, None, spacing)
      if menu_cmdlines > 1:
        grubconf.write('}\n')

    make_fwsetup(d, grubconf)
}

do_compile[vardeps] += "KERNEL_IMAGETYPE PREFERRED_PROVIDER_virtual/kernel MENDER_GRUBCONF_KERNELS_MENU MENDER_GRUBCONF_KERNELS"

MENDER_GRUBCONF_PREFIX ??= "/EFI/BOOT"
do_install(){
	install -d -m 755 ${D}${datadir}/${BPN} "${D}${EFI_PREFIX}${MENDER_GRUBCONF_PREFIX}"
	install -m 644 ${WORKDIR}/${BPN}.conf.sample ${D}${datadir}/${BPN}
	install -m 644 ${WORKDIR}/${BPN}.conf.sample ${D}${EFI_PREFIX}${MENDER_GRUBCONF_PREFIX}/grub.cfg	
}

FILES_${PN} = "${datadir} ${EFI_PREFIX}${MENDER_GRUBCONF_PREFIX}"

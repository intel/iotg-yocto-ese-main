DESCRIPTION = "Grub boot configuration file to support mender updates"

# In local.conf, do
# grubconf settings (does not handle kernel image dependecies)
# MENDER_GRUBCONF_KERNELS_MENU[kernel recipe name] = "menu entry names..."
# MENDER_GRUBCONF_KERNELS[menu entry name] = "command line additions...."

SRC_URI = "file://${BPN}.header file://${BPN}.defaults"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PROVIDES = "virtual/grub-bootconf"

INHIBIT_DEFAULT_DEPS = "1"
inherit gnu-efi grub-efi-cfg
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

def make_chainloaders(d, fd):
  for entry in d.getVarFlags('MENDER_GRUBCONF_CHAINLOADER') or '':
    chainloader = d.getVarFlag('MENDER_GRUBCONF_CHAINLOADER', entry)
    fd.write('menuentry \'chainload %s\' {\n' % entry )
    fd.write('  chainloader "%s"\n' % chainloader)
    fd.write('  boot\n')
    fd.write('  echo "chainloaded program [%s] returned, rebooting..."\n' % chainloader)
    fd.write('  sleep 5\n')
    fd.write('  reboot\n')
    fd.write('}\n')

python do_compile(){
  wd = d.getVar('WORKDIR')
  bpn = d.getVar('BPN')
  cmdline = d.getVar('APPEND')
  common_cmd = 'root=${mender_root} ' + cmdline
  image_type = d.getVar('KERNEL_IMAGETYPE')
  want_submenu = bool(d.getVar('MENDER_GRUBCONF_USE_SUBMENU'))
  want_load_defaults = bool(d.getVar('MENDER_GRUBCONF_TRY_LOAD_DEFAULTS'))

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

    ### try load defaults file if exists
    if want_load_defaults:
      with open(os.path.join(wd, bpn + '.defaults')) as defaults:
        grubconf.write(defaults.read())

    # special case entry for default kernel parameter extras, not to be common for all
    default_additions = d.getVar('MENDER_GRUBCONF_KERNELS_DEFAULT') or ''
    make_menuentry(d, grubconf, 'boot ' + kernel, image_type + '-kernel', common_cmd + default_additions, None)

    for entry in sorted(d.getVarFlags('MENDER_GRUBCONF_KERNELS_MENU') or []):
      menu_names = d.getVarFlag('MENDER_GRUBCONF_KERNELS_MENU', entry) or ''
      menu_name = sorted(menu_names.strip().split())
      menu_cmdlines = len(menu_name)
      spacing = ''
      if want_submenu and menu_cmdlines > 1:
        grubconf.write('submenu \'boot %s (menu)\' {\n' % entry)
        spacing = '  '
      for name in menu_name:
        k_cmdline = d.getVarFlag('MENDER_GRUBCONF_KERNELS', name)
        specific_cmdline = common_cmd
        if k_cmdline is not None:
          specific_cmdline += ' %s' % k_cmdline
        make_menuentry(d, grubconf, 'boot ' + name, image_type + '-' + entry + '-kernel', specific_cmdline, None, spacing)
      if want_submenu and menu_cmdlines > 1:
        grubconf.write('}\n')

    make_chainloaders(d, grubconf)
    make_fwsetup(d, grubconf)
}

do_compile[vardeps] += "KERNEL_IMAGETYPE PREFERRED_PROVIDER_virtual/kernel MENDER_GRUBCONF_KERNELS_MENU MENDER_GRUBCONF_KERNELS MENDER_GRUBCONF_CHAINLOADER MENDER_GRUBCONF_KERNELS_DEFAULT MENDER_GRUBCONF_USE_SUBMENU MENDER_GRUBCONF_TRY_LOAD_DEFAULTS"

# try load ${prefix}/grub-default.cfg for default boot entry? set to blank to disable
MENDER_GRUBCONF_TRY_LOAD_DEFAULTS ??= "1"
# set to empty to flatten menu, any value to use submenus
MENDER_GRUBCONF_USE_SUBMENU ??= ""
# extra arguments to the default kernel cmdline that aren't common for the rest
MENDER_GRUBCONF_KERNELS_DEFAULT ??= ""
MENDER_GRUBCONF_PREFIX ??= "/EFI/BOOT"
do_install(){
	install -d -m 755 ${D}${datadir}/${BPN} "${D}${EFI_PREFIX}${MENDER_GRUBCONF_PREFIX}"
	install -m 644 ${WORKDIR}/${BPN}.conf.sample ${D}${datadir}/${BPN}
	install -m 644 ${WORKDIR}/${BPN}.conf.sample ${D}${EFI_PREFIX}${MENDER_GRUBCONF_PREFIX}/grub.cfg	
}

FILES_${PN} = "${datadir} ${EFI_PREFIX}${MENDER_GRUBCONF_PREFIX}"

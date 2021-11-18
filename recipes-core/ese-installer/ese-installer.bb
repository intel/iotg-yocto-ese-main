DESCRIPTION = "Installer Image helper recipe"
LICENSE = "MIT"
ESE_INSTALLER_WKS_FILE ??= "ese-installer.wks.in"
WKS_FILE_forcevariable = "${ESE_INSTALLER_WKS_FILE}"
inherit core-image
INHIBIT_DEFAULT_DEPS = "1"


IMAGE_FEATURES_append = " read-only-rootfs"
PACKAGE_INSTALL = "ese-installer-init"


do_image[recrdeptask] += "do_image_deps"
addtask do_image_deps
python do_image_deps(){
}

python(){
  import re
  for v in ['ESE_INSTALLER_PAYLOAD_TASK', 'ESE_INSTALLER_INITRD_TASK']:
    task = d.getVar(v) or ''
    if not bool(task):
      continue
    for t in task.split():
      if re.match("^(mc|multiconfig):", t):
        flag = "mcdepends"
      else:
        flag = "depends"
      d.appendVarFlag('do_image_deps', flag, ' %s' % t)
}


ESE_INSTALLER_PAYLOAD_TASK ??= "mc:${BB_CURRENT_MC}:x86:core-image-sato-sdk:do_image_squashfs_lz4 mc:${BB_CURRENT_MC}:x86:core-image-sato-sdk:do_image_complete"
ESE_INSTALLER_PAYLOAD_IMAGE ??= "${TOPDIR}/tmp-x86-glibc/deploy/images/intel-corei7-64/core-image-sato-sdk-intel-corei7-64.squashfs-lz4"

ESE_INSTALLER_INITRD_TASK ??= "ese-installer-initramfs:do_image_complete"
ESE_INSTALLER_INITRD_IMAGE ??= "ese-installer-initramfs-intel-corei7-64.cpio"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SUMMARY = "Basic grub.cfg for use in EFI PXE systems"
DESCRIPTION = "Grub might require different configuration file for \
different machines."
HOMEPAGE = "https://www.gnu.org/software/grub/manual/grub/grub.html#Configuration"

RPROVIDES:${PN} += "virtual/grub-bootconf"
PROVIDES += "virtual/grub-bootconf"
require conf/image-uefi.conf
inherit grub-efi-cfg image-uefi-netboot-paths

S = "${WORKDIR}"

do_compile(){
cat > ${S}/grub-bootconf << STOP
set default=0
set timeout=${GRUB_TIMEOUT}

  menuentry  'Yocto Boot' --class yocto1 --class gnu-linux --class gnu --class os {
    linuxefi ${UEFI_NETBOOT_KERNEL} ${APPEND}
    initrdefi ${UEFI_NETBOOT_INITRD}
  }
STOP
}

do_install() {
	install -d ${D}${EFI_FILES_PATH}
	install grub-bootconf ${D}${EFI_FILES_PATH}/grub.cfg
}

FILES:${PN} = "${EFI_FILES_PATH}/grub.cfg"
SYSROOT_DIRS:append = " ${EFI_FILES_PATH}"

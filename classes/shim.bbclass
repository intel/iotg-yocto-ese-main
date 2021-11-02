# EFI_PROVIDER class for live-vm-common.bbclass/image-live.bbclass (hddimg)
# wraps around real provider, set SHIM_LOADER_PROVIDER

inherit ${SHIM_LOADER_PROVIDER}
do_bootimg[depends] += "virtual/secure-boot-certificates:do_deploy shim:do_deploy ${SHIM_LOADER_PROVIDER}:do_deploy sbsigntool-native:do_populate_sysroot"

SHIM_LOADER_IMAGE ??= "grub${SHIM_LOADER_SUFFIX}.efi"
SHIM_LOADER_PROVIDER ??= "grub-efi"

# EFI suffixes
inherit gnu-efi
SHIM_LOADER_SUFFIX = "${GNU_EFI_ARCH}"

# sanity check
python(){
  if not d.getVar('SHIM_LOADER_SUFFIX'):
    bb.error('Unknown architecture, SHIM_LOADER_SUFFIX is unset')
}

# overwrite live-vm-common and insert shim
# signs the existing bootloader, moves it to SHIM_LOADER_IMAGE and place shim as EFI_BOOT_IMAGE
# EFI_BOOT_IMAGE should usually be boot${SHIM_LOADER_SUFFIX}.efi
#
# yocto.key and yocto.cer comes from the shim package, can be randomly generated or predefined
# see virtual/secure-boot-certificates provider recipe
# yocto.crt Machine Owner Key Cert, generated from shim or predefined from virtual/secure-boot-certificates
# yocto.key ditto
# db.key - DB key inserted into firmware
# db.crt - DB cert inserted into firmware
efi_populate_common:append() {
	sbsign --key "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.key" --cert "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.crt" --output "${DEST}${EFIDIR}/${SHIM_LOADER_IMAGE}" "${DEST}${EFIDIR}/${EFI_BOOT_IMAGE}"
	rm "${DEST}${EFIDIR}/${EFI_BOOT_IMAGE}"
	sbsign --key "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.key" --cert "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.crt" -o "${DEST}${EFIDIR}/${EFI_BOOT_IMAGE}" "${DEPLOY_DIR_IMAGE}/shim${SHIM_LOADER_SUFFIX}.efi"
	install -m 644 "${DEPLOY_DIR_IMAGE}/mm${SHIM_LOADER_SUFFIX}.efi.signed" "${DEST}${EFIDIR}/mm${SHIM_LOADER_SUFFIX}.efi"
}

# sign the kernel
populate_kernel:append() {
	if [ -f $dest/${KERNEL_IMAGETYPE} ]; then
		sbsign --key "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.key" --cert "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.crt" -o "$dest/${KERNEL_IMAGETYPE}".signed "$dest/${KERNEL_IMAGETYPE}"
		mv $dest/${KERNEL_IMAGETYPE}.signed $dest/${KERNEL_IMAGETYPE}
	fi
}

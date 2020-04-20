# EFI_PROVIDER class for live-vm-common.bbclass/image-live.bbclass (hddimg)
# wraps around real provider, set SHIM_LOADER_PROVIDER

inherit ${SHIM_LOADER_PROVIDER}
do_bootimg[depends] += "shim:do_deploy ${SHIM_LOADER_PROVIDER}:do_deploy sb-keymgmt-native:do_populate_sysroot sbsigntool-native:do_populate_sysroot"

SHIM_LOADER_IMAGE ??= "grub${SHIM_LOADER_SUFFIX}.efi"
SHIM_LOADER_PROVIDER ??= "grub-efi"

# EFI suffixes
inherit gnu-efi
SHIM_LOADER_SUFFIX = "${GNU_EFI_ARCH}"

# sanity check
python(){
  if not d.getVar('SHIM_LOADER_SUFFIX'):
    bb.error('Unknown architecture, SHIM_LOADER_SUFFIX is unset')
  if not d.getVar('DB_KEY_PATH'):
    bb.error('DB_KEY_PATH is unset!')
  if not d.getVar('DB_CERT_PATH'):
    bb.error('DB_CERT_PATH is unset!')
}

# overwrite live-vm-common and insert shim
# signs the existing bootloader, moves it to SHIM_LOADER_IMAGE and place shim as EFI_BOOT_IMAGE
# EFI_BOOT_IMAGE should usually be boot${SHIM_LOADER_SUFFIX}.efi
#
# yocto.key and yocto.cer comes from the shim package, can be randomly generated or predefined
# see shim recipe
# yocto.crt Machine Owner Key Cert, generated from shim or predefined from MOK_CERT_PATH
# yocto.key ditto for MOK_KEY_PATH
# DB_KEY_PATH - DB key inserted into firmware
# DB_CERT_PATH - DB cert inserted into firmware
efi_populate_common_append() {
	sb-keymgmt.py -c sign -kn "${DEPLOY_DIR_IMAGE}/yocto.key" -cn "${DEPLOY_DIR_IMAGE}/yocto.crt" -usf "${DEST}${EFIDIR}/${EFI_BOOT_IMAGE}" -sf "${DEST}${EFIDIR}/${SHIM_LOADER_IMAGE}"
	rm "${DEST}${EFIDIR}/${EFI_BOOT_IMAGE}"
	sb-keymgmt.py -c sign -kn "${DB_KEY_PATH}" -cn "${DB_CERT_PATH}" -usf "${DEPLOY_DIR_IMAGE}/shim${SHIM_LOADER_SUFFIX}.efi" -sf "${DEST}${EFIDIR}/${EFI_BOOT_IMAGE}"
	install -m 644 "${DEPLOY_DIR_IMAGE}/mm${SHIM_LOADER_SUFFIX}.efi.signed" "${DEST}${EFIDIR}/mm${SHIM_LOADER_SUFFIX}.efi"
}

# sign the kernel
populate_kernel_append() {
	if [ -f $dest/${KERNEL_IMAGETYPE} ]; then
		sb-keymgmt.py -c sign -kn "${DEPLOY_DIR_IMAGE}/yocto.key" -cn "${DEPLOY_DIR_IMAGE}/yocto.crt" -usf "$dest/${KERNEL_IMAGETYPE}" -sf "$dest/${KERNEL_IMAGETYPE}".signed
		mv $dest/${KERNEL_IMAGETYPE}.signed $dest/${KERNEL_IMAGETYPE}
	fi
}

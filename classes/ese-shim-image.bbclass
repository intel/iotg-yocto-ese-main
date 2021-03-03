do_rootfs[depends] += "shim:do_deploy"

do_ese_shim_install(){
	install -m644 "${DEPLOY_DIR_IMAGE}/shim/shim${GNU_EFI_ARCH}.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
	install -m644 "${DEPLOY_DIR_IMAGE}/shim/mm${GNU_EFI_ARCH}.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
	if test -n "${ESE_INSTALL_SHIM_FALLBACK}"; then
		install -m644 "${DEPLOY_DIR_IMAGE}/shim/fb${GNU_EFI_ARCH}.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
	fi
}

python(){
    d.appendVar('ESE_IMAGE_CALLS', ' do_ese_shim_install;')
}

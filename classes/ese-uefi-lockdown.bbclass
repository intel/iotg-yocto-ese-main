# efitools recipe not yet fully tested for public release
do_rootfs[depends] += "efitools:do_deploy"

do_ese_efitools_install(){
	install -m644 "${DEPLOY_DIR_IMAGE}/efitools/KeyTool${GNU_EFI_ARCH}.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
	install -m644 "${DEPLOY_DIR_IMAGE}/efitools/LockDown${GNU_EFI_ARCH}.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
}

python(){
    d.appendVar('ESE_IMAGE_CALLS', ' do_ese_efitools_install;')
}

do_image[depends] += "ptcm:do_deploy"

do_ese_copy_ptcm(){
	install -m644 "${DEPLOY_DIR_IMAGE}/ptcm/ptcm_boot.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
	install -m644 "${DEPLOY_DIR_IMAGE}/ptcm/ptcm_rtdrv.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
}

python(){
    d.appendVar('IMAGE_PREPROCESS_COMMAND', ' do_ese_copy_ptcm;')
}

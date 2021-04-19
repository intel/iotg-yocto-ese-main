do_image[depends] += "ptcm:do_deploy"

do_ese_copy_ptcm(){
	install -m644 "${DEPLOY_DIR_IMAGE}/ptcm/rtcm_boot.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
	install -m644 "${DEPLOY_DIR_IMAGE}/ptcm/rtcm_rtdrv.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
}

python(){
    d.appendVar('ESE_IMAGE_CALLS', ' do_ese_copy_ptcm;')
}

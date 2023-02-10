ESE_IMAGE_FIXUP_DEPS += "ptcm:do_deploy"

do_ese_copy_rtcm(){
	install -m644 "${DEPLOY_DIR_IMAGE}/ptcm/rtcm_boot.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
	install -m644 "${DEPLOY_DIR_IMAGE}/ptcm/rtcm_rtdrv.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
}

inherit ${@bb.utils.contains_any('IMAGE_FEATURES', [ 'slimboot' ], 'python3native', '', d)}
ESE_IMAGE_FIXUP_DEPS += "${@bb.utils.contains_any('IMAGE_FEATURES', [ 'slimboot' ], "virtual/secure-boot-certificates-slimboot:do_deploy slimboot-tools-native:do_populate_sysroot ${PYTHON_PN}-cryptography-native:do_populate_sysroot ${PYTHON_PN}-idna-native:do_populate_sysroot sbl-rtcm:do_deploy", '', d)}"
SBLIMAGE_AUTH ??= ""
do_ese_copy_rtcm_slimboot(){
	local auth
	if test -n "${SBLIMAGE_AUTH}"; then
		auth="-a ${SBLIMAGE_AUTH}"
	else
		auth=""
	fi
	${PYTHON} ${STAGING_DIR_NATIVE}/${libexecdir}/slimboot/Tools/GenContainer.py create \
		-cl RTCM:${DEPLOY_DIR_IMAGE}/sbl-rtcm/sbl-rtcm.efi -o ${WORKDIR}/sbl_rtcm \
		-k ${DEPLOY_DIR_IMAGE}/secure-boot-certificates-slimboot/SigningKey.pem ${auth}
	install -m644 ${WORKDIR}/sbl_rtcm "${IMAGE_ROOTFS}/boot"
}

python(){
  d.appendVar('ESE_IMAGE_CALLS', ' do_ese_copy_rtcm;')
  features = d.getVar('IMAGE_FEATURES')
  if 'slimboot' in features:
    d.appendVar('ESE_IMAGE_CALLS', ' do_ese_copy_rtcm_slimboot;')
}

do_install:append:class-target() {
	install -d ${D}/boot
	${STAGING_DIR_NATIVE}${sbindir_native}/iucode_tool \
		${UCODE_FILTER_PARAMETERS} \
		--overwrite \
		--write-earlyfw=${D}/boot/intel-uc.img \
		${S}/intel-ucode/* ${S}/intel-ucode-with-caveats/*
}

PACKAGES += "${PN}-bootimg"
FILES:${PN}-bootimg = "/boot/intel-uc.img"
S = "${WORKDIR}/git"

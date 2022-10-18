# BUG! firmware should into /lib/firmware, not lib64
# New split format for newer kernels, overwrites main recipe do_install
do_install() {
	install -d ${D}${nonarch_base_libdir}/firmware/intel-ucode/
	${STAGING_DIR_NATIVE}${sbindir_native}/iucode_tool \
	--write-firmware=${D}${nonarch_base_libdir}/firmware/intel-ucode \
	${S}/intel-ucode/* ${S}/intel-ucode-with-caveats/*
}

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
FILES:${PN} = "${nonarch_base_libdir}"

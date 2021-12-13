SUMMARY = "X11 runtime environment variable for video playback"
DESCRIPTION = "Ensure environment variables propagate to x11 started through the login or the system init process"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
	file://libva_env.sh.in \
	file://igfx_user_feature.txt \
"

FILES:${PN} = "${sysconfdir}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
	install -m 0755 -d ${D}${sysconfdir}/profile.d
	sed -e "s!@LIBDIR@!${libdir}!g" < ${WORKDIR}/libva_env.sh.in > ${WORKDIR}/libva_env.sh
	install -m 0644 ${WORKDIR}/libva_env.sh ${D}${sysconfdir}/profile.d
	install -m 0644 ${WORKDIR}/igfx_user_feature.txt ${D}${sysconfdir}/
}

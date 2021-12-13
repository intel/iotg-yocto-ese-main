SUMMARY = "Startup script and systemd unit file for various module loading for specific platforms"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://audio-hda.sh"

do_install() {
        install -Dm0755 ${WORKDIR}/audio-hda.sh ${D}${sbindir}/audio-hda.sh
	install -d -m 0755 ${D}${sysconfdir}/preinit-env
	ln -s ${sbindir}/audio-hda.sh ${D}${sysconfdir}/preinit-env/
}

FILES:${PN} += "\
    ${sbindir}/audio-hda.sh \
    ${sysconfdir} \
"

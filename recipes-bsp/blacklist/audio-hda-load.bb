SUMMARY = "Startup script and systemd unit file for various module loading for specific platforms"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " file://audio-hda.service \
            file://audio-hda.sh \
          "

inherit systemd

REQUIRED_DISTRO_FEATURES= "systemd"

SYSTEMD_SERVICE_${PN} = "audio-hda.service"

SYSTEMD_AUTO_ENABLE_${PN} ?= "enable"

do_install() {
        install -Dm0644 ${WORKDIR}/audio-hda.service ${D}${systemd_system_unitdir}/audio-hda.service
        install -Dm0755 ${WORKDIR}/audio-hda.sh ${D}${sbindir}/audio-hda.sh
}

FILES_${PN} += "\
    ${systemd_system_unitdir}/audio-hda.service \
    ${sbindir}/audio-hda.sh \
"

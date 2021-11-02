LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

RDEPENDS:${PN} = "pulseaudio-server"

do_install() {
	mkdir -p ${D}${ROOT_HOME}/.config/systemd/user/default.target.wants ${D}${ROOT_HOME}/.config/systemd/user/sockets.target.wants
	ln -s ${libdir}/systemd/user/pulseaudio.service ${D}${ROOT_HOME}/.config/systemd/user/default.target.wants/pulseaudio.service
	ln -s ${libdir}/systemd/user/pulseaudio.socket ${D}${ROOT_HOME}/.config/systemd/user/sockets.target.wants/pulseaudio.socket
}

FILES:${PN} = "${ROOT_HOME}"


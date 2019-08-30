SUMMARY = "Include blacklist.conf to /etc/modprobe.d"
DESCRIPTION = "Prevent list of drivers from autoload"
SECTION = "examples"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://blacklist.conf"

S = "${WORKDIR}"

FILES_${PN} += "${sysconfdir}/modprobe.d/blacklist.conf"

do_install() {
    install -d 0644 ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${S}/blacklist.conf ${D}${sysconfdir}/modprobe.d/blacklist.conf
}
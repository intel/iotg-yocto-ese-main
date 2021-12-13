DESCRIPTION = "Dynamically point to primary/secondary partition"
SRC_URI = "file://mender.conf.in"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
RDEPENDS:${PN} = "gawk grep sed util-linux-findfs util-linux-fsck"

inherit allarch

do_install(){
	install -d ${D}${sysconfdir}/mender
	install -m 644 ${WORKDIR}/mender.conf.in ${D}${sysconfdir}/mender
}

FILES:${PN} = "${sysconfdir}/mender"

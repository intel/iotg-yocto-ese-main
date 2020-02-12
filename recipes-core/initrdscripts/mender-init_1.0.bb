SUMMARY = "Helper startup script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://${BPN}.sh"
RDEPENDS_${PN} += "busybox eudev"

S = "${WORKDIR}"

do_install() {
	install -m 0755 ${WORKDIR}/${PN}.sh ${D}/init
	install -d ${D}/dev
	mknod -m 622 ${D}/dev/console c 5 1
}

inherit allarch

FILES_${PN} += "/init /dev/console"


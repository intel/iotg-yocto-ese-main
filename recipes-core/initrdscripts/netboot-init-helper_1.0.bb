SUMMARY = "Helper startup script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://${BPN}.sh"
RDEPENDS_${PN} += "iscsi-initiator-utils busybox-udhcpc"
RPROVIDES_${PN} = "virtual/netboot-netmount"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/${BPN}.sh ${D}/${sysconfdir}/init.d/netmount
}

inherit allarch

FILES_${PN} += "${sysconfdir}/init.d"

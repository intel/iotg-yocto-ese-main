DESCRIPTION = "Subregion capsule generation tool"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae11844b70d3f1dac86ad0125ab1cd60"

PV = "0.0.0+git${SRCPV}"
SRC_URI = "git://github.com/intel/iotg-fbu;protocol=https;nobranch=1"
SRCREV = "e21bcce54a6d5da79ba524dfa35abdaa95c5c8b2"

S = "${WORKDIR}/git"
inherit python3native
DEPENDS += "${PYTHON_PN}-cryptography ${PYTHON_PN}-click openssl"
RDEPENDS_${PN} += "${PYTHON_PN}-cryptography ${PYTHON_PN}-click"
BBCLASSEXTEND = "native"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
	install -d ${D}${libexecdir}
	cp -r ${S}/siiptool ${D}${libexecdir}/${PN}
}

FILES_${PN} = "${libexecdir}"

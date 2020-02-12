DESCRIPTION = "Slim Bootloader is an open-source boot firmware solution"
SRC_URI = "git://github.com/slimbootloader/slimbootloader;protocol=https"
SRCREV = "8bbef289f3f18e961deae4510d2f378afe7d2d33"
PV = "0.0.0+git${SRCPV}"
LICENSE = "BSD-2-Clause-Patent"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d1ed89007e7aa232a4dc1c59b6c9efc4"
S = "${WORKDIR}/git"
inherit python3native
DEPENDS += "${PYTHON_PN}-cryptography ${PYTHON_PN}-idna"
RDEPENDS_${PN} += "${PYTHON_PN}-cryptography ${PYTHON_PN}-idna"
BBCLASSEXTEND = "native"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
	install -m 755 -d ${D}${libexecdir}/slimboot/Tools
	install -m 755 ${S}/BootloaderCorePkg/Tools/*.py ${D}${libexecdir}/slimboot/Tools
}

FILES_${PN} = "${libexecdir}"

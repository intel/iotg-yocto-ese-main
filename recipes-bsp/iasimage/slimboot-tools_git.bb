DESCRIPTION = "Slim Bootloader is an open-source boot firmware solution"
SRC_URI = "git://github.com/slimbootloader/slimbootloader;protocol=https"
SRCREV = "28c003976385d77b3a46414372deb1630a9c680e"
PV = "0.0.0+git${SRCPV}"
LICENSE = "BSD-2-Clause-Patent"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ef7fba7be2819ac13aaf5d0f842ce5d9"
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

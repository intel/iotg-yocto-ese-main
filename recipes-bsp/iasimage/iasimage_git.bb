DESCRIPTION = "iasimage is a utility program for creating Intel Automotive Service (IAS) images"
SRC_URI = "git://github.com/intel/iasimage;protocol=https"
SRCREV = "7799ac734164c9120e7aee838a4cb7ab8c2005a1"
PV = "0.0.2+git${SRCPV}"
LICENSE = "BSD-3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ce12cc3293b6d1b8acee76c79d7e04cc"
S = "${WORKDIR}/git"
inherit python3native
DEPENDS += "${PYTHON_PN}-cryptography ${PYTHON_PN}-idna"
RDEPENDS_${PN} += "${PYTHON_PN}-cryptography ${PYTHON_PN}-idna"
BBCLASSEXTEND = "native"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
	install -D --mode=0755 iasimage.py ${D}${bindir}/iasimage
}

FILES_${PN} = "${bindir}"

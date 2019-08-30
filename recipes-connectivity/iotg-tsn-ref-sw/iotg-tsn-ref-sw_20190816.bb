SUMMARY = "IOTG TSN Reference Software"
HOMEPAGE = "https://github.com/intel/iotg_tsn_ref_sw"
AUTHOR = "Wong, Vincent Por Yin"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3f809c79d03d707b90fb032c7f82350a"
FILESEXTRAPATHS_prepend := "${TSN_REFKIT_PATH}:"

SRC_URI = "file://iotg-tsn-ref-sw-${PV}.tar.gz"
SRC_URI[md5sum] = "b97527f14a158956ccddf59201263ea0"
SRC_URI[sha256sum] = "2f67ab81da9b636f753f7137507963aabaf760d8660f5747d55f463efd8b93b7"

S = "${WORKDIR}/${BPN}"

RDEPENDS_${PN} += "gnuplot-x11 bash open62541"
inherit allarch

FILES_${PN} += "/opt/intel"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install(){
    install -m 755 -d ${D}/opt/intel/
    cp --no-preserve=ownership -r ${S} ${D}/opt/intel/
}

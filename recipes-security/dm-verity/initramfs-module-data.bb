SUMMARY = "initramfs support for locating and mounting the data partition"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS_${PN} = "initramfs-framework-base"

SRC_URI = "file://data"

FILES_${PN} = "/init.d/91-data"

do_install() {
    install -d ${D}/init.d/
    install -m 0755 ${WORKDIR}/data ${D}/init.d/91-data
}

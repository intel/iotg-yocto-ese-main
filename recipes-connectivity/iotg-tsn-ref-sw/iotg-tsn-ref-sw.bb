SUMMARY = "IOTG TSN Reference Software"
DESCRIPTION = "IOTG Time-Sensitive Networking Reference Software"
HOMEPAGE = "https://github.com/intel/iotg_tsn_ref_sw"
AUTHOR = "Wong, Vincent Por Yin"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://LICENSE.md;md5=665202835d998903d52afcb9c30ad9f5"

SRC_URI = "git://github.com/intel/iotg_tsn_ref_sw.git;protocol=https;branch=master"

SRCREV = "0d1057d2dd324f5a6cd9e949b6d551738826e783"
PV = "1.0-git${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

DEPENDS += " elfutils libbpf open62541-iotg json-c"

RDEPENDS_${PN} += "\
                    gnuplot-x11 \
                    iperf3 \
                    "

do_install_append(){
    mkdir -p ${D}${datadir}/${BPN}
    cd ${S} && git archive HEAD | tar -C ${D}${datadir}/${BPN} -xf -
}

RRECOMMENDS_${PN}-src = "\
                        libbpf-dev \
                        open62541-iotg-dev \
                        "


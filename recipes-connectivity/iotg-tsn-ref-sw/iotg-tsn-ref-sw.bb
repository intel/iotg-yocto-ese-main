SUMMARY = "IOTG TSN Reference Software"
DESCRIPTION = "IOTG Time-Sensitive Networking Reference Software"
HOMEPAGE = "https://github.com/intel/iotg_tsn_ref_sw"
AUTHOR = "Wong, Vincent Por Yin"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://LICENSE.md;md5=665202835d998903d52afcb9c30ad9f5"

SRC_URI = "git://github.com/intel/iotg_tsn_ref_sw.git;protocol=https;branch=master"

SRCREV = "14ca2bb7a1d1558d42fc6b52b5767044c1705444"
PV = "1.0-git${SRCPV}"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

DEPENDS += " elfutils libbpf open62541-iotg json-c"

RDEPENDS:${PN} += "\
                    gnuplot-x11 \
                    iperf3 \
                    bash \
                    "
do_compile:append(){
  cd ${S} && git archive HEAD > ${B}/pack.tar
}

do_install:append(){
    mkdir -p ${D}${datadir}/${BPN}
    tar -C ${D}${datadir}/${BPN} -xf ${B}/pack.tar
}

RRECOMMENDS:${PN}-src = "\
                        libbpf-dev \
                        open62541-iotg-dev \
                        "


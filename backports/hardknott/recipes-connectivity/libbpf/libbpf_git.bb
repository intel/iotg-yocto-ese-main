SUMMARY = "Automated upstream mirror for libbpf stand-alone build"
HOMEPAGE = "https://github.com/libbpf/libbpf"
AUTHOR = "Nakryiko, Andrii"
LICENSE = "LGPL-2.1 | BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.BSD-2-Clause;md5=5d6306d1b08f8df623178dfd81880927 \
                    file://LICENSE.LPGL-2.1;md5=b370887980db5dd40659b50909238dbd "

SRC_URI =" \
          git://github.com/libbpf/libbpf.git \
          file://0002-makefile-don-t-preserve-ownership-when-installing-fr.patch \
          file://0001-libbpf-add-txtime-field-in-xdp_desc-struct.patch \
          "

inherit pkgconfig

DEPENDS = "elfutils"

# Added to make it default version for 5.10 kernel. Once 5.12 kernel added we 
# need to remove this line and we should use from meta-oe
DEFAULT_PREFERENCE = "1"

SRCREV = "d1fd50d475779f64805fdc28f912547b9e3dee8a"
S = "${WORKDIR}/git"
PV = "0.2-git${SRCPV}"

EXTRA_OEMAKE += "-C ${S}/src"

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake DESTDIR=${D} install
}


SUMMARY = "Automated upstream mirror for libbpf stand-alone build"
HOMEPAGE = "https://github.com/libbpf/libbpf"
AUTHOR = "Nakryiko, Andrii"
LICENSE = "LGPL-2.1 | BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.BSD-2-Clause;md5=5d6306d1b08f8df623178dfd81880927 \
                    file://LICENSE.LPGL-2.1;md5=b370887980db5dd40659b50909238dbd "

SRC_URI =" \
          git://github.com/libbpf/libbpf.git \
          file://0002-makefile-don-t-preserve-ownership-when-installing-fr.patch \
          file://0003-makefile-remove-check-for-reallocarray.patch \
          file://0001-libbpf-add-txtime-field-in-xdp_desc-struct.patch \
          "

inherit pkgconfig

DEPENDS = "elfutils"

SRCREV = "ab067ed3710550c6d1b127aac6437f96f8f99447"
S = "${WORKDIR}/git"
PV = "0.0.6-git${SRCPV}"

EXTRA_OEMAKE += "-C ${S}/src"

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake DESTDIR=${D} install
}


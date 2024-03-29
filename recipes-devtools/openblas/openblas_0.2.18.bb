DESCRIPTION = "OpenBLAS is an optimized BLAS library based on GotoBLAS2 1.13 BSD version."
SUMMARY = "OpenBLAS : An optimized BLAS library"
AUTHOR = "Alexander Leiva <norxander@gmail.com>"
HOMEPAGE = "http://www.openblas.net/"
PRIORITY= "optional"
SECTION = "libs"
LICENSE = "BSD"
PR = "r0"

DEPENDS = "make"

LIC_FILES_CHKSUM = "file://LICENSE;md5=5adf4792c949a00013ce25d476a2abc0"

SRC_URI = "https://github.com/xianyi/OpenBLAS/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "805e7f660877d588ea7e3792cda2ee65"
SRC_URI[sha256sum] = "7d9f8d4ea4a65ab68088f3bb557f03a7ac9cb5036ef2ba30546c3a28774a4112"

S = "${WORKDIR}/OpenBLAS-${PV}"

def map_arch(a, d):
        import re
        if re.match('i.86$', a): return 'ATOM'
        elif re.match('x86_64$', a): return 'ATOM'
        elif re.match('aarch32$', a): return 'CORTEXA9'
        elif re.match('aarch64$', a): return 'ARMV8'
        return a

def map_bits(a, d):
        import re
        if re.match('i.86$', a): return 32
        elif re.match('x86_64$', a): return 64
        elif re.match('aarch32$', a): return 32
        elif re.match('aarch64$', a): return 64
        return 32

do_compile () {
        oe_runmake HOSTCC="${BUILD_CC}"                                         \
                                CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" \
                                ONLY_CBLAS=1 BINARY='${@map_bits(d.getVar('TARGET_ARCH', True), d)}' \
                                TARGET='${@map_arch(d.getVar('TARGET_ARCH', True), d)}'
}

do_install() {
        oe_runmake HOSTCC="${BUILD_CC}"                                         \
                                CC="${TARGET_PREFIX}gcc ${TOOLCHAIN_OPTIONS}" \
                                ONLY_CBLAS=1 BINARY='${@map_bits(d.getVar('TARGET_ARCH', True), d)}' \
                                TARGET='${@map_arch(d.getVar('TARGET_ARCH', True), d)}' \
                                PREFIX=${D}/usr install
}

do_install:append() {
        rm -rf ${D}/usr/bin
        rm -rf ${D}/usr/lib/cmake
}

FILES:${PN}     = "${libdir}/*"
FILES:${PN}-dev = "${includedir} ${libdir}/lib${PN}.so"


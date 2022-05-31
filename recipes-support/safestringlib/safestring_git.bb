SUMMARY = "Safe string operations and memory routines"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE&COPYING.txt;md5=edd6f8c4c1072d72645ae28cb04bdbce \
                    file://LICENSE;md5=54f68ba2c33474320fcc46b1f2d01249"

SRC_URI = "git://github.com/intel/safestringlib.git;protocol=https;branch=master \
           file://0001-CMakeLists.txt-remove-ROP-mitigation.patch"

PV = "1.0.0+git${SRCPV}"
SRCREV = "5f8ce199e491c5bea65fdef85b671a25f252768d"

S = "${WORKDIR}/git"

inherit cmake

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    install -m 0644 ${B}/libsafestring_shared.so ${D}${libdir}
    install -m 0644 ${B}/libsafestring_static.a ${D}${libdir}
    install -m 0644 ${S}/include/*.h ${D}${includedir}
}

SOLIBS = ".so"
FILES_SOLIBSDEV = ""

LICENSE = "KhronosFreeUse"
LIC_FILES_CHKSUM = "file://LICENSE;md5=dcefc90f4c3c689ec0c2489064e7273b"

SRC_URI = "git://github.com/KhronosGroup/OpenCL-Headers;branch=master"
SRCREV = "40c5d226c7c0706f0176884e9b94b3886679c983"

S = "${WORKDIR}/git"

PROVIDES += "virtual/opencl-headers"
BBCLASSEXTEND = "native"
RDEPENDS_${PN}-dev = ""

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install() {
    mkdir -p ${D}${includedir}/CL
    install -m644 ${S}/CL/*.h ${D}${includedir}/CL
}

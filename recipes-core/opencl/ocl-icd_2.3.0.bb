LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=1238d5bccbb6bda30654e48dcc0a554b"

SRC_URI = "https://github.com/OCL-dev/ocl-icd/archive/refs/tags/v${PV}.tar.gz"
SRC_URI[md5sum] = "7c34ddef4a662f6737e3165ccd417cba"
SRC_URI[sha256sum] = "469f592ccd9b0547fb7212b17e1553b203d178634c20d3416640c0209e3ddd50"

inherit autotools

DEPENDS += "ruby-native"

BBCLASSEXTEND = "native"

PROVIDES = "virtual/opencl-icd"

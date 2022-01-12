SUMMARY = "Alliance for Open Media - AV1 Codec Library"
DESCRIPTION = "Alliance for Open Media AV1 codec library"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6ea91368c1bbdf877159435572b931f5"

SRC_URI = "git://aomedia.googlesource.com/aom;protocol=https"

SRCREV = "d1d1226af626a61f7ca664b270dd473b92228984"

S = "${WORKDIR}/git"

DEPENDS:append = " yasm-native"

inherit pkgconfig cmake perlnative python3native
EXTRA_OECMAKE:append = " -DBUILD_SHARED_LIBS=1"

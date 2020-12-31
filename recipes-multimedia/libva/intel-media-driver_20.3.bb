SUMMARY = "VA driver for Intel Gen based graphics hardware"
DESCRIPTION = "Intel Media Driver for VAAPI is a new VA-API (Video Acceleration API) \
user mode driver supporting hardware accelerated decoding, encoding, \
and video post processing for GEN based graphics hardware."

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

LICENSE = "MIT & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0 \
                    file://media_driver/media_libvpx.LICENSE;md5=d5b04755015be901744a78cc30d390d4 \
                    "
# Only for 64 bit until this is resolved: https://github.com/intel/media-driver/issues/356
COMPATIBLE_HOST = '(x86_64).*-linux'

REQUIRED_DISTRO_FEATURES = "opengl"

DEPENDS += "libva gmmlib"

SRC_URI = "git://github.com/intel/media-driver.git;protocol=https;nobranch=1 \
	   file://0001-Encode-Fix-sporadic-JPEGe-failure-on-JSL-EHL.patch \
	   file://0002-Encode-Moved-JPEGe-status-report-from-common-code.patch \
          "

PV = "20.3+git${SRCPV}"
SRCREV = "86ec0b6f61862806d8aed1803537728d1dfadf84"
S = "${WORKDIR}/git"

COMPATIBLE_HOST_x86-x32 = "null"

UPSTREAM_CHECK_GITTAGREGEX = "^intel-media-(?P<pver>(?!600\..*)\d+(\.\d+)+)$"

inherit cmake pkgconfig

MEDIA_DRIVER_ARCH_x86    = "32"
MEDIA_DRIVER_ARCH_x86-64 = "64"

EXTRA_OECMAKE += " \
                   -DMEDIA_RUN_TEST_SUITE=OFF \
                   -DARCH=${MEDIA_DRIVER_ARCH} \
                   -DMEDIA_BUILD_FATAL_WARNINGS=OFF \
                   -DMEDIA_VERSION=2.0.0 \
		   -DBYPASS_MEDIA_ULT=yes \
                   -DCenc_Decode_Supported=no \
		  "

do_configure_prepend_toolchain-clang() {
    sed -i -e '/-fno-tree-pre/d' ${S}/media_driver/cmake/linux/media_compile_flags_linux.cmake
}

# See: https://github.com/intel/media-driver/issues/358
FILES_${PN} += " \
                 ${libdir}/dri/ \
                 ${libdir}/igfxcmrt64.so \
                 "


SUMMARY = "Intel Media Driver for VAAPI"
LICENSE = "MIT & BSD"

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

LICENSE = "MIT & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0 \
                    file://media_driver/media_libvpx.LICENSE;md5=d5b04755015be901744a78cc30d390d4 \
                    "

SRC_URI = "git://github.com/intel/media-driver.git;protocol=https;branch=intel-media-19.3"

SRCREV = "2f1adfcecb9f8f050a78def3027149156da63a3e"
S = "${WORKDIR}/git"

DEPENDS += "libva gmmlib"
MEDIA_DRIVER_ARCH_x86    = "32"
MEDIA_DRIVER_ARCH_x86-64 = "64"


inherit pkgconfig cmake

EXTRA_OECMAKE = "-DMEDIA_VERSION=2.0.0 -DBYPASS_MEDIA_ULT=yes -DCenc_Decode_Supported=no"

FILES_${PN} += "${libdir}/dri/iHD_drv_video.so \
		 ${libdir}/igfxcmrt64.so \ "

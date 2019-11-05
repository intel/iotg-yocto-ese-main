SUMMARY = "Intel Media Driver for VAAPI"
LICENSE = "MIT & BSD"

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

LICENSE = "MIT & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0 \
                    file://media_driver/media_libvpx.LICENSE;md5=d5b04755015be901744a78cc30d390d4 \
                    "

SRC_URI = "git://github.com/intel/media-driver.git;protocol=https \
	   file://0001-VP-Fix-GPU-hang.patch \
	   file://0001-VP-Fix-Gpu-hang-decode.patch \
"
PV = "19.3+git${SRCPV}"
SRCREV = "3c73afffc7899c02a3eae9feb01b702b34039840"
S = "${WORKDIR}/git"

DEPENDS += "libva gmmlib"
MEDIA_DRIVER_ARCH_x86    = "32"
MEDIA_DRIVER_ARCH_x86-64 = "64"


inherit pkgconfig cmake

EXTRA_OECMAKE = "-DMEDIA_VERSION=2.0.0 -DBYPASS_MEDIA_ULT=yes -DCenc_Decode_Supported=no"

FILES_${PN} += "${libdir}/dri/iHD_drv_video.so \
		 ${libdir}/igfxcmrt64.so \ "

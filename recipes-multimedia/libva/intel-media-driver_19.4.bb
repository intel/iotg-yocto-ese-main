SUMMARY = "Intel Media Driver for VAAPI"
LICENSE = "MIT & BSD"

HOMEPAGE = "https://github.com/intel/media-driver"
BUGTRACKER = "https://github.com/intel/media-driver/issues"

LICENSE = "MIT & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=6aab5363823095ce682b155fef0231f0 \
                    file://media_driver/media_libvpx.LICENSE;md5=d5b04755015be901744a78cc30d390d4 \
                    "

SRC_URI = " \
    git://github.com/intel/media-driver.git;protocol=https;branch=intel-media-19.4 \
    file://0001-Fix-VC1-decode-output-corruption-on-GEN12.patch \
    file://0002-Gen12-hevc-fix-caps-query-for-10-bits-decoder.patch \
    file://0001-Set-the-value-for-unknown-or-unsupported-attributes-.patch \
    file://0002-Support-up-to-8Kx8K-VP9-encoding-on-GEN12.patch \
    file://0003-Enable-the-WA-for-slice-header-inserting-on-GEN12.patch \
"

PV = "19.4+git${SRCPV}"
SRCREV = "12b7fcded6c74377ecf57eb8258f5e3d55ca722e"
S = "${WORKDIR}/git"

DEPENDS += "libva gmmlib"
MEDIA_DRIVER_ARCH_x86    = "32"
MEDIA_DRIVER_ARCH_x86-64 = "64"


inherit pkgconfig cmake

EXTRA_OECMAKE = "-DMEDIA_VERSION=2.0.0 -DBYPASS_MEDIA_ULT=yes -DCenc_Decode_Supported=no"

FILES_${PN} += "${libdir}/dri/iHD_drv_video.so \
		 ${libdir}/igfxcmrt64.so \ "

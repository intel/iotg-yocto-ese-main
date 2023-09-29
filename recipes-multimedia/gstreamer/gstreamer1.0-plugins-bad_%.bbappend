FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

DEPENDS += "libgudev onevpl onevpl-intel-gpu"

SRC_URI:append = "\
        file://0001-msdkmjpegdec-Add-support-for-error-report.patch \
        file://0001-msdk-Use-local-cflags-for-configuration.patch \
        file://0002-msdk-Add-extra-build-for-another-msdk-onevpl.patch \
        file://0001-Add-jpeg-error-report-to-GST-MSDK-onevpl.patch \
        "

PACKAGECONFIG:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'msdk', 'msdk', '', d)}"

PACKAGECONFIG[msdk] = "-Dmsdk=enabled -Dmfx_api=MSDK,-Dmsdk=disabled,libgudev intel-mediasdk"

FILES:${PN}-msdk-vpl += "${libdir}/gstreamer-1.0-msdk-onevpl/*.so"

PACKAGES += "${PN}-msdk-vpl"

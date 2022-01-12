DEPENDS += "libgudev onevpl onevpl-intel-gpu"

SRC_URI:append = "\
        file://0001-msdk-Use-local-cflags-for-configuration.patch \
        file://0002-msdk-Add-extra-build-for-another-msdk-onevpl.patch \
        "
FILES:${PN}-msdk-vpl += "${libdir}/gstreamer-1.0-msdk-onevpl/*.so"

PACKAGES += "${PN}-msdk-vpl"

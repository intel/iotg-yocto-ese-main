FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
        file://eglimage-add-Y210-Y212_LE-Y212_BE-Y410-Y412_LE-DMABuf-support.patch \
        "

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
        file://0001-vaapi-decoder-Adding-back-missing-DMABuf-in-src-caps.patch \
        file://0002-vaapi-decoder-modify-the-condition-to-judge-whether-.patch \
        "
PACKAGECONFIG:append = " egl"

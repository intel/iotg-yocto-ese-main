FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Add-new-BMG-Device-ID-234.patch \
                 "
S = "${WORKDIR}/git"


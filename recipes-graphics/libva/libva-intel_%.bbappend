FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-va-add-av1-profile2.patch \
                   file://0002-va-add-VAProfileH264High422.patch \
                 "
S = "${WORKDIR}/git"

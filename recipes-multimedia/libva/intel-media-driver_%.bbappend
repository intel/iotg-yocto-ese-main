FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Set-Scanout-by-default.patch \
                   file://0001-Revert-VP-fourcc-format-mismatch.patch \
                 "

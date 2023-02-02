FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Revert-Enable-CM-copy-by-default-on-TGL-RKL-ADL-Linu.patch \
                   file://0001-decode-Fix-no-free-surface-in-decoder-p-3972.patch \
                 "

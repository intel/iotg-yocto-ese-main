FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Fix-missing-UYVY-VA_FOURCC-causing-encode-failure.patch \
                   file://0001-Enable-xdg_shell-for-weston10.patch \
                 "

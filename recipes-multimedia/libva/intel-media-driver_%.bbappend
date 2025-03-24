FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0005-Fix-ACM-multi-channel-AVC-encode-performance-degrada.patch \
                   file://0007-VP-Fix-GST-VA-Video-output-of-Color-conversion-UYVY-.patch \
                 "

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0005-Fix-ACM-multi-channel-AVC-encode-performance-degrada.patch \
                   file://0007-Add-BMG-E-NEX1-and-NEX2-Device-ID.patch \
                   file://0008-Media-Common-Add-profile-entrypoint-check.patch \
                   file://0009-Decode-Disable-Xe3_Lpm-VD-SFC.patch \
                   file://0010-Upstream-BMG-DevID-upstream.patch \
                   file://0011-Add-AVC-10b-decode-for-PTL.patch \
                 "
S = "${WORKDIR}/git"


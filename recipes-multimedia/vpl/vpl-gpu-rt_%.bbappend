FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-avce-Use-VDEnc-for-YUY2-AYUV-RGB-formats.patch \
                   file://0001-Add-BMG-E-NEX1-and-NEX2-Device-ID.patch \
                   file://0002-RT-Common-Add-BMG-Device-ID-for-upstream.patch \
                   file://0001-AVCd-Add-AVC-10bit-Dec-for-PTL.patch \
                 "
S = "${WORKDIR}/git"


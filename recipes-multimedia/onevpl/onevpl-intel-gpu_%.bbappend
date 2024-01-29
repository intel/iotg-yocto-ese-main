FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-RT-Common-Support-new-DID-6092.patch \
                   file://0001-Jpege-Enable-BGR4-JPEG-Enc-support.patch \
                   file://0001-avce-Use-VDEnc-for-YUY2-AYUV-RGB-formats.patch \
                 "

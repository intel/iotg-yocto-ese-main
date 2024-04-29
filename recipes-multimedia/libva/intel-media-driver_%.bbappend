FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0003-Force-enable-MTL-Media-CCS-by-default.patch \
                   file://0013-VP-Fix-failed-4k-video-wall-test-case-and-color.patch \
                   file://0001-Fix-BGRx-AVC-HEVC-Enc-for-g12.patch \
                   file://0001-VP-Fix-4K-monitor-failed-to-run-videowall-4k-on-mult.patch \
                   file://0001-Add-ACM-new-DIDs.patch \
                 "

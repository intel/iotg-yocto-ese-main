require gstreamer1.0-plugins-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
			"

SRC_URI = " \
    gitsm://github.com/GStreamer/gst-plugins-base.git;protocol=https \
    file://get-caps-from-src-pad-when-query-caps.patch \
    file://0006-glimagesink-Downrank-to-marginal.patch \
"

SRCREV = "ed651022cb798b578ca4141a5f2add1d515dfd12" 

S = "${WORKDIR}/git"

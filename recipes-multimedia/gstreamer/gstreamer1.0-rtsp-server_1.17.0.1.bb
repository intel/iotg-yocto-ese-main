SUMMARY = "A library on top of GStreamer for building an RTSP server"
SECTION = "multimedia"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"


SRC_URI = "gitsm://github.com/GStreamer/gst-rtsp-server.git;protocol=https \
           "
SRCREV = "8d4a9f37e506929808106b361dc6af02ee8d1984"

S = "${WORKDIR}/git"

inherit meson pkgconfig upstream-version-is-even 


FILES_${PN} += "${libdir}/gstreamer-1.0/libgstrtspclientsink.so"

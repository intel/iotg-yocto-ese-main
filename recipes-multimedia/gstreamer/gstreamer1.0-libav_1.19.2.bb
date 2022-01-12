SUMMARY = "Libav-based GStreamer 1.x plugin"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
SECTION = "multimedia"

LICENSE = "GPLv2+ & LGPLv2+ & ( (GPLv2+ & LGPLv2.1+) | (GPLv3+ & LGPLv3+) )"
LICENSE_FLAGS = "commercial"
LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770 \
                   "

SRC_URI = "gitsm://github.com/GStreamer/gst-libav.git;protocol=https \
           "

S = "${WORKDIR}/git"
SRCREV = "4f649c9556ce5b4501363885995554598303e01c"


DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base ffmpeg"

inherit meson pkgconfig upstream-version-is-even

FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-staticdev += "${libdir}/gstreamer-1.0/*.a"

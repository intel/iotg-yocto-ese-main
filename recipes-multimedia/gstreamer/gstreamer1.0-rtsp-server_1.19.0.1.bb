SUMMARY = "A library on top of GStreamer for building an RTSP server"
HOMEPAGE = "http://cgit.freedesktop.org/gstreamer/gst-rtsp-server/"
SECTION = "multimedia"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

SRC_URI = "gitsm://github.com/GStreamer/gst-rtsp-server.git;protocol=https \
           "

S = "${WORKDIR}/git"
SRCREV = "c747711ac5562e53f90ed96e87776a2e6b9806ba"


inherit meson pkgconfig upstream-version-is-even gobject-introspection

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Dexamples=disabled \
    -Dtests=disabled \
"

GIR_MESON_ENABLE_FLAG = "enabled"
GIR_MESON_DISABLE_FLAG = "disabled"

# Starting with 1.8.0 gst-rtsp-server includes dependency-less plugins as well
require gstreamer1.0-plugins-packaging.inc

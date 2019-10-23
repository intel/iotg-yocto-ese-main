require gstreamer1.0-vaapi.inc

SRC_URI = " \
	gitsm://github.com/GStreamer/gstreamer-vaapi.git;protocol=https \
	file://0001-Add-EGL_CFLAGS-to-libgstvaapi-CFLAGS.patch \
"

SRCREV = "40bcefcb3ba4c250ac26a4f504cd17b427bc1802"

DEPENDS += "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"

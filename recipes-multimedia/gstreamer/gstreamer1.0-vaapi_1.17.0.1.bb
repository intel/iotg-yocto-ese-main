require gstreamer1.0-vaapi.inc

SRC_URI = " \
	gitsm://github.com/GStreamer/gstreamer-vaapi.git;protocol=https \
"

SRCREV = "1168d6d5481a4d3cd83847b0ff7239231c29df5c"

DEPENDS += "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"

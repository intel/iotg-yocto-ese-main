require gstreamer1.0-vaapi.inc

SRC_URI = " \
	gitsm://github.com/GStreamer/gstreamer-vaapi.git;protocol=https \
	file://0001-Add-EGL_CFLAGS-to-libgstvaapi-CFLAGS.patch \
	file://0001-libs-video-format-Improve-the-video-format-mapping.patch \
"

SRCREV = "8b8dfb127a7d8d9e84c749b6a4b9c2ed12338e29"

DEPENDS += "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"

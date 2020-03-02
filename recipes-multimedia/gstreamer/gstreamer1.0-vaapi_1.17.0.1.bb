require gstreamer1.0-vaapi.inc

SRC_URI = " \
	gitsm://github.com/GStreamer/gstreamer-vaapi.git;protocol=https \
        file://0001-Revert-vaapipostproc-only-set-VPP-colorimetry-when-V.patch \
        file://0002-Revert-vaapipostproc-set-vpp-filter-colorimetry.patch \
"

SRCREV = "3ff51a6e5232ce529777ce5856c2afc12526a9c2"

DEPENDS += "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"

require gstreamer1.0-vaapi.inc

SRC_URI = " \
	gitsm://github.com/GStreamer/gstreamer-vaapi.git;protocol=https \
        file://0001-libs-decoder-h265-set-parser-info-state-at-decoding-.patch \
        file://0002-Revert-vaapivideobufferpool-validate-returned-meta.patch \
        file://0003-Revert-vaapivideobufferpool-always-update-release-th.patch \
"

SRCREV = "566af572afc2a91d58d24cc8630b462c86903d7e"

DEPENDS += "gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"

FILESEXTRAPATHS_prepend := "${THISDIR}/files/weston:"

SRC_URI_append = "\
           file://weston.png \
           file://weston.desktop \
           file://xwayland.weston-start \
"
DEPENDS_append = " ffmpeg gstreamer1.0 gstreamer1.0-plugins-base"

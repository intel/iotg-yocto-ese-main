require gstreamer1.0.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
	gitsm://github.com/GStreamer/gstreamer.git;protocol=https"

SRCREV = "4af103d124310ca8c4c101c890e309592031e513"

S = "${WORKDIR}/git"

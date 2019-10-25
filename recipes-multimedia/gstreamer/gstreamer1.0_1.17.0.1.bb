require gstreamer1.0.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

SRC_URI = " \
	gitsm://github.com/GStreamer/gstreamer.git;protocol=https"

SRCREV = "b50abd9f4ebe0ed1981578ae3e55ae2cbe6f57fb"

S = "${WORKDIR}/git"

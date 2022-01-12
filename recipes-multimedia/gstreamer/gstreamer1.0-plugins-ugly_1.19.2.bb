require gstreamer1.0-plugins-common.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343" 

LICENSE = "GPLv2+ & LGPLv2.1+ & LGPLv2+"
LICENSE_FLAGS = "commercial"

SRC_URI = " \
            gitsm://github.com/GStreamer/gst-plugins-ugly.git;protocol=https \
            "

S = "${WORKDIR}/git"
SRCREV = "499d3cd726a4ca9cbbdd4b4fe9ccdca78ef538ba"

DEPENDS += "gstreamer1.0-plugins-base"

GST_PLUGIN_SET_HAS_EXAMPLES = "0"

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    a52dec mpeg2dec \
"

PACKAGECONFIG[a52dec]   = "-Da52dec=enabled,-Da52dec=disabled,liba52"
PACKAGECONFIG[amrnb]    = "-Damrnb=enabled,-Damrnb=disabled,opencore-amr"
PACKAGECONFIG[amrwb]    = "-Damrwbdec=enabled,-Damrwbdec=disabled,opencore-amr"
PACKAGECONFIG[cdio]     = "-Dcdio=enabled,-Dcdio=disabled,libcdio"
PACKAGECONFIG[dvdread]  = "-Ddvdread=enabled,-Ddvdread=disabled,libdvdread"
PACKAGECONFIG[mpeg2dec] = "-Dmpeg2dec=enabled,-Dmpeg2dec=disabled,mpeg2dec"
PACKAGECONFIG[x264]     = "-Dx264=enabled,-Dx264=disabled,x264"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Dsidplay=disabled \
"

FILES:${PN}-amrnb += "${datadir}/gstreamer-1.0/presets/GstAmrnbEnc.prs"
FILES:${PN}-x264 += "${datadir}/gstreamer-1.0/presets/GstX264Enc.prs"


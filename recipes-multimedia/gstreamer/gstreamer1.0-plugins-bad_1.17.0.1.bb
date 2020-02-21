require gstreamer1.0-plugins-bad.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 "

SRC_URI = " \
    gitsm://github.com/GStreamer/gst-plugins-bad;protocol=https \
    file://fix-maybe-uninitialized-warnings-when-compiling-with-Os.patch \
    file://avoid-including-sys-poll.h-directly.patch \
    file://ensure-valid-sentinels-for-gst_structure_get-etc.patch \
    file://0001-msdk-ignore-MFX_ERR_UNDEFINED_BEHAVIOR-when-loading-.patch \
    file://0001-msdkvp9enc-fix-width-and-height.patch \
    file://0001-msdkvp9enc-fix-10bit-encoding.patch \
    file://0001-msdkvp9enc-output-raw-vp9-stream-instead-of-IVF-stre.patch \
"

SRCREV = "526afac73681450a7dae4d9b8db9fcb039da5311"

S = "${WORKDIR}/git"


PACKAGECONFIG[egl]              = "-Degl=enabled,-Degl=disabled,gegl"
PACKAGECONFIG[opengl]          = "-Dgl=enabled,-Dgl=disabled,libglu"

#PACKAGECONFIG[opengl]          = "libglu"


EXTRA_OECONF_remove = " \
--disable-cocoa \
--disable-qt \
"

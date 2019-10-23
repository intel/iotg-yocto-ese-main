require gstreamer1.0-plugins-bad.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=73a5855a8119deb017f5f13cf327095d \
                    file://COPYING.LIB;md5=21682e4e8fea52413fd26c60acb907e5 "

SRC_URI = " \
    gitsm://github.com/GStreamer/gst-plugins-bad;protocol=https \
    file://configure-allow-to-disable-libssh2.patch \
    file://fix-maybe-uninitialized-warnings-when-compiling-with-Os.patch \
    file://avoid-including-sys-poll.h-directly.patch \
    file://ensure-valid-sentinels-for-gst_structure_get-etc.patch \
    file://0001-h265parse-Fix-for-st_rps_bits-calculation.patch \
    file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
    file://0001-solve-msdk-mfx-file-path-error.patch \
    file://0001-solve-path-msdk.patch \
"

SRCREV = "e5279cd97abe3ff52fa916afcc7897402f7e2eba"

S = "${WORKDIR}/git"

PACKAGECONFIG[egl]             = "virtual/egl"
PACKAGECONFIG[schroedinger]    = "--enable-schro,schroedinger"
PACKAGECONFIG[gles2]           = "virtual/libgles2"
PACKAGECONFIG[gtk]             = "--enable-gtk3,gtk+3"
PACKAGECONFIG[opengl]          = "virtual/libgl libglu"

EXTRA_OECONF_remove = " \
--disable-cocoa \
--disable-qt \
"

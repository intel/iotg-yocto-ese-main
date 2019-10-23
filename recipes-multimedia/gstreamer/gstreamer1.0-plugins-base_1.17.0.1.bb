require gstreamer1.0-plugins-base.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d \
                    file://common/coverage/coverage-report.pl;beginline=2;endline=17;md5=a4e1830fce078028c8f0974161272607"

SRC_URI = " \
    gitsm://github.com/GStreamer/gst-plugins-base.git;protocol=https \
    file://0012-Makefile.am-prefix-calls-to-pkg-config-with-PKG_CONF.patch \
    file://get-caps-from-src-pad-when-query-caps.patch \
    file://make-gio_unix_2_0-dependency-configurable.patch \
    file://0001-introspection.m4-prefix-pkgconfig-paths-with-PKG_CON.patch \
    file://0005-gstreamer-gl.pc.in-don-t-append-GL_CFLAGS-to-CFLAGS.patch \
    file://0006-glimagesink-Downrank-to-marginal.patch \
"

SRCREV = "2409f4f3609d2dce3f65d1a8c4be5e470b7cf9d3"

S = "${WORKDIR}/git"

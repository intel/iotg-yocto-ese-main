SUMMARY = "The Cairo 2D vector graphics library"
DESCRIPTION = "Cairo is a multi-platform library providing anti-aliased \
vector-based rendering for multiple target backends. Paths consist \
of line segments and cubic splines and can be rendered at any width \
with various join and cap styles. All colors may be specified with \
optional translucence (opacity/alpha) and combined using the \
extended Porter/Duff compositing algebra as found in the X Render \
Extension."
HOMEPAGE = "http://cairographics.org"
BUGTRACKER = "http://bugs.freedesktop.org"
SECTION = "libs"

LICENSE = "MPL-1.0 & LGPL-2.1-only & GPL-3.0-or-later"
LICENSE:${PN} = "MPL-1.0 & LGPL-2.1-only"
LICENSE:${PN}-dev = "MPL-1.0 & LGPL-2.1-only"
LICENSE:${PN}-doc = "MPL-1.0 & LGPL-2.1-only"
LICENSE:${PN}-gobject = "MPL-1.0 & LGPL-2.1-only"
LICENSE:${PN}-script-interpreter = "MPL-1.0 & LGPL-2.1-only"
LICENSE:${PN}-perf-utils = "GPL-3.0-or-later"

LIC_FILES_CHKSUM = "file://COPYING;md5=e73e999e0c72b5ac9012424fa157ad77"

DEPENDS = "fontconfig glib-2.0 libpng pixman zlib"

SRC_URI = "https://cairographics.org/snapshots/cairo-${PV}.tar.xz"

SRC_URI[md5sum] = "321a07adaeb125cb5513079256d465fb"
SRC_URI[sha256sum] = "6b70d4655e2a47a22b101c666f4b29ba746eda4aa8a0f7255b32b2e9408801df"

inherit autotools pkgconfig upstream-version-is-even gtk-doc multilib_script

MULTILIB_SCRIPTS = "${PN}-perf-utils:${bindir}/cairo-trace"

X11DEPENDS = "virtual/libx11 libsm libxrender libxext"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'directfb', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11 xcb', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'opengl', '', d)}"

PACKAGECONFIG[x11] = "--with-x=yes -enable-xlib,--with-x=no --disable-xlib,${X11DEPENDS}"
PACKAGECONFIG[xcb] = "--enable-xcb,--disable-xcb,libxcb"
PACKAGECONFIG[directfb] = "--enable-directfb=yes,,directfb"
PACKAGECONFIG[valgrind] = "--enable-valgrind=yes,--disable-valgrind,valgrind"
PACKAGECONFIG[egl] = "--enable-egl=yes,--disable-egl,virtual/egl"
PACKAGECONFIG[glesv2] = "--enable-glesv2,--disable-glesv2,virtual/libgles2"
PACKAGECONFIG[opengl] = "--enable-gl,--disable-gl,virtual/libgl"

EXTRA_OECONF += " \
    ${@bb.utils.contains('TARGET_FPU', 'soft', '--disable-some-floating-point', '', d)} \
    --enable-tee \
"

# We don't depend on binutils so we need to disable this
export ac_cv_lib_bfd_bfd_openr="no"
# Ensure we don't depend on LZO
export ac_cv_lib_lzo2_lzo2a_decompress="no"

do_install:append () {
	rm -rf ${D}${bindir}/cairo-sphinx
	rm -rf ${D}${libdir}/cairo/cairo-fdr*
	rm -rf ${D}${libdir}/cairo/cairo-sphinx*
	rm -rf ${D}${libdir}/cairo/.debug/cairo-fdr*
	rm -rf ${D}${libdir}/cairo/.debug/cairo-sphinx*
}

PACKAGES =+ "cairo-gobject cairo-script-interpreter cairo-perf-utils"

SUMMARY:cairo-gobject = "The Cairo library GObject wrapper library"
DESCRIPTION:cairo-gobject = "A GObject wrapper library for the Cairo API."

SUMMARY:cairo-script-interpreter = "The Cairo library script interpreter"
DESCRIPTION:cairo-script-interpreter = "The Cairo script interpreter implements \
CairoScript.  CairoScript is used by tracing utilities to enable the ability \
to replay rendering."

DESCRIPTION:cairo-perf-utils = "The Cairo library performance utilities"

FILES:${PN} = "${libdir}/libcairo.so.*"
FILES:${PN}-gobject = "${libdir}/libcairo-gobject.so.*"
FILES:${PN}-script-interpreter = "${libdir}/libcairo-script-interpreter.so.*"
FILES:${PN}-perf-utils = "${bindir}/cairo-trace* ${libdir}/cairo/*.la ${libdir}/cairo/libcairo-trace.so.*"
FILES:${PN}-dev += "${libdir}/cairo/*.so"
# skip for libcairo-trace.so
INSANE_SKIP:${PN}-dev += "dev-elf"

BBCLASSEXTEND = "native"

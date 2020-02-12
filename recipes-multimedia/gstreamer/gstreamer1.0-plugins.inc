SUMMARY = "Plugins for the GStreamer multimedia framework 1.x"
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"

DEPENDS = "gstreamer1.0 glib-2.0-native"

inherit meson pkgconfig upstream-version-is-even

LIBV = "1.0"
require gst-plugins-package.inc

# Orc enables runtime JIT compilation of data processing routines from Orc
# bytecode to SIMD instructions for various architectures (currently SSE, MMX,
# MIPS, Altivec and NEON are supported).

GSTREAMER_ORC ?= "orc"

PACKAGECONFIG[debug] = "-Ddebug=true,-Ddebug=false"
PACKAGECONFIG[orc] = "-Dorc=enabled,-Dorc=disabled,orc orc-native"
PACKAGECONFIG[valgrind] = "-Dvalgrind=disable,-Dvalgrind=enabled,valgrind"

export ORCC = "${STAGING_DIR_NATIVE}${bindir}/orcc"


PACKAGES_DYNAMIC = "^${PN}-.*"


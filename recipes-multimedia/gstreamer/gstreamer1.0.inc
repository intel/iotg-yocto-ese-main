SUMMARY = "GStreamer 1.0 multimedia framework"
DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPLv2+"

DEPENDS = "glib-2.0 glib-2.0-native libcap libxml2 bison-native flex-native"

inherit meson pkgconfig gettext upstream-version-is-even

PACKAGECONFIG ??= ""

PACKAGECONFIG[debug] = "-Denable-debug=false,-Ddisable-debug=true"
PACKAGECONFIG[valgrind] = "-Denable-valgrind=true,-Ddisable-valgrind=false,valgrind,"
PACKAGECONFIG[gst-tracer-hooks] = "-Denable-gst-tracer-hooks=true,-Ddisable-gst-tracer-hooks=false,"
PACKAGECONFIG[unwind] = "-Dwith-unwind=true,-Dwithout-unwind=false,libunwind"
PACKAGECONFIG[dw] = "-Dwith-dw=true,-Dwithout-dw=false,elfutils"



PACKAGES += "${PN}-bash-completion"

FILES_${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-1.0/*.la ${libdir}/gstreamer-1.0/*.a ${libdir}/gstreamer-1.0/include"
FILES_${PN}-bash-completion += "${datadir}/bash-completion/completions/ ${datadir}/bash-completion/helpers/gst*"

RRECOMMENDS_${PN}_qemux86 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"
RRECOMMENDS_${PN}_qemux86-64 += "kernel-module-snd-ens1370 kernel-module-snd-rawmidi"


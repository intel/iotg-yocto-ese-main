SUMMARY = "GStreamer 1.0 multimedia framework"
DESCRIPTION = "GStreamer is a multimedia framework for encoding and decoding video and sound. \
It supports a wide range of formats including mp3, ogg, avi, mpeg and quicktime."
HOMEPAGE = "http://gstreamer.freedesktop.org/"
BUGTRACKER = "https://bugzilla.gnome.org/enter_bug.cgi?product=Gstreamer"
SECTION = "multimedia"
LICENSE = "LGPL-2.1-or-later"

DEPENDS = "glib-2.0 glib-2.0-native libxml2 bison-native flex-native"

inherit meson pkgconfig gettext upstream-version-is-even gobject-introspection ptest-gnome

LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770 \
                    file://gst/gst.h;beginline=1;endline=21;md5=e059138481205ee2c6fc1c079c016d0d"

S = "${WORKDIR}/gstreamer-${PV}"

SRC_URI = "https://gstreamer.freedesktop.org/src/gstreamer/gstreamer-${PV}.tar.xz \
           file://run-ptest \
           file://0001-tests-respect-the-idententaion-used-in-meson.patch \
           file://0002-tests-add-support-for-install-the-tests.patch \
           file://0003-tests-use-a-dictionaries-for-environment.patch;striplevel=3 \
           file://0004-tests-add-helper-script-to-run-the-installed_tests.patch;striplevel=3 \
           file://0001-tools-gst-stats-drop-use-of-GSlice-allocator.patch \
           file://0002-core-tests-drop-use-of-GSlice-allocator.patch \
           file://0003-core-examples-drop-use-of-GSlice.patch \
           file://0004-gstparse-drop-use-of-GSlice-allocator.patch \
           file://0005-registry-drop-use-of-GSlice.patch \
           file://0006-registrychunks-get-rid-of-internal-GST_REGISTRY_CHUN.patch \
           file://0007-allocator-buffer-bufferlist-drop-use-of-GSlice.patch \
           file://0008-toc-tocsetter-drop-use-of-GSlice.patch \
           file://0009-taglist-tagsetter-drop-use-of-GSlice.patch \
           file://0010-bin-drop-use-of-GSlice.patch \
           file://0011-bus-poll-drop-use-of-GSlice.patch \
           file://0012-caps-capsfeatures-drop-use-of-GSlice.patch \
           file://0013-gstvalue-drop-use-of-GSlice.patch \
           file://0014-message-drop-use-of-GSlice.patch \
           file://0015-meta-drop-use-of-GSlice.patch \
           file://0016-uri-drop-use-of-GSlice.patch \
           file://0017-context-drop-use-of-GSlice.patch \
           file://0018-clockentry-drop-use-of-GSlice.patch \
           file://0019-datetime-drop-use-of-GSlice.patch \
           file://0020-devicemonitor-drop-use-of-GSlice.patch \
           file://0021-info-drop-use-of-GSlice.patch \
           file://0022-event-drop-use-of-GSlice.patch \
           file://0023-iterator-drop-use-of-GSlice.patch \
           file://0024-query-drop-use-of-GSlice.patch \
           file://0025-sample-drop-use-of-GSlice.patch \
           file://0026-segment-drop-use-of-GSlice.patch \
           file://0027-taskpool-drop-use-of-GSlice.patch \
           file://0028-tracerutils-drop-use-of-GSlice.patch \
           file://0029-controller-drop-use-of-GSlice.patch \
           file://0030-gstcheck-drop-use-of-GSlice.patch \
           file://0031-baseparse-drop-use-of-GSlice.patch \
           file://0032-libs-base-drop-use-of-GSlice.patch \
           file://0033-tracers-drop-use-of-GSlice.patch \
           file://0034-queue2-drop-use-of-GSlice.patch \
           file://0035-multiqueue-drop-use-of-GSlice.patch \
           file://0036-inputselector-drop-use-of-GSlice.patch \
           file://0037-base-bitwriter-drop-use-of-GSlice.patch \
           file://0038-bin-Do-not-deactivate-pad-in-NULL_TO_READY.patch \
           file://0039-gstvalue-Implement-union-for-GstFractionRange.patch \
           file://0040-basesink-Add-GST_BASE_SINK_FLOW_DROPPED-return-value.patch \
           file://0041-gstreamer-re-indent-with-GNU-indent-2.2.12.patch \
           file://0042-plugin-ext-dep-INFO-Adding-var-name-and-value-part-n.patch \
           "

SRC_URI[sha256sum] = "f500e6cfddff55908f937711fc26a0840de28a1e9ec49621c0b6f1adbd8f818e"

PACKAGECONFIG ??= "${@bb.utils.contains('PTEST_ENABLED', '1', 'tests', '', d)} \
                   check \
                   debug \
                   tools"

PACKAGECONFIG[debug] = "-Dgst_debug=true,-Dgst_debug=false"
PACKAGECONFIG[tracer-hooks] = "-Dtracer_hooks=true,-Dtracer_hooks=false"
PACKAGECONFIG[coretracers] = "-Dcoretracers=enabled,-Dcoretracers=disabled"
PACKAGECONFIG[check] = "-Dcheck=enabled,-Dcheck=disabled"
PACKAGECONFIG[tests] = "-Dtests=enabled -Dinstalled_tests=true,-Dtests=disabled -Dinstalled_tests=false"
PACKAGECONFIG[unwind] = "-Dlibunwind=enabled,-Dlibunwind=disabled,libunwind"
PACKAGECONFIG[dw] = "-Dlibdw=enabled,-Dlibdw=disabled,elfutils"
PACKAGECONFIG[bash-completion] = "-Dbash-completion=enabled,-Dbash-completion=disabled,bash-completion"
PACKAGECONFIG[tools] = "-Dtools=enabled,-Dtools=disabled"
PACKAGECONFIG[setcap] = "-Dptp-helper-permissions=capabilities,,libcap libcap-native"

# TODO: put this in a gettext.bbclass patch
def gettext_oemeson(d):
    if d.getVar('USE_NLS') == 'no':
        return '-Dnls=disabled'
    # Remove the NLS bits if USE_NLS is no or INHIBIT_DEFAULT_DEPS is set
    if d.getVar('INHIBIT_DEFAULT_DEPS') and not oe.utils.inherits(d, 'cross-canadian'):
        return '-Dnls=disabled'
    return '-Dnls=enabled'

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Dexamples=disabled \
    -Ddbghelp=disabled \
    ${@gettext_oemeson(d)} \
"

GIR_MESON_ENABLE_FLAG = "enabled"
GIR_MESON_DISABLE_FLAG = "disabled"

PACKAGES += "${PN}-bash-completion"

# Add the core element plugins to the main package
FILES:${PN} += "${libdir}/gstreamer-1.0/*.so"
FILES:${PN}-dev += "${libdir}/gstreamer-1.0/*.a ${libdir}/gstreamer-1.0/include"
FILES:${PN}-bash-completion += "${datadir}/bash-completion/completions/ ${datadir}/bash-completion/helpers/gst*"
FILES:${PN}-dbg += "${datadir}/gdb ${datadir}/gstreamer-1.0/gdb"

RDEPENDS:${PN}-ptest:append:libc-glibc = " glibc-gconv-iso8859-5"

CVE_PRODUCT = "gstreamer"

PTEST_BUILD_HOST_FILES = ""

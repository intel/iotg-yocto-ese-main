FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRCREV_COMMON = "bb8f66983af6b7f38dc80efa6b6ca8f34c2ab85e"
SRC_URI:append = " file://client/0002-channel-display-gst-Add-byte-stream-as-the-stream-fo.patch \
                   file://client/0003-channel-display-gst-Prefer-playbin3-to-playbin.patch \
                   file://client/0004-gstreamer-Avoid-dangling-pointers-in-free_pipeline.patch \
                   file://client/0005-channel-display-gst-Use-h-w-based-decoders-with-Inte.patch \
                   file://client/0006-gstreamer-Fix-leak-using-GstBus-watch.patch \
                   file://client/0007-gstreamer-Fallback-to-S-W-decoder-if-H-W-one-is-not-.patch \
                 "

do_patch:prepend() {
    bb.build.exec_func('do_fix_spice_common', d)
}
do_fix_spice_common() {
        cd ${S}/subprojects/spice-common && git checkout ${SRCREV_COMMON}
        cd ${S} && git add subprojects/spice-common && git commit -m "update spice-common"
}

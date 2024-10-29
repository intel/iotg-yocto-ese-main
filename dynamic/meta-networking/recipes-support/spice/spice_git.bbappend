FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRCREV_COMMON = "bb8f66983af6b7f38dc80efa6b6ca8f34c2ab85e"
SRC_URI:append = " file://server/0002-server-add-SSL_OP_NO_RENEGOTIATION-fallback-path.patch \
                   file://server/0003-build-Remove-support-for-GStreamer-0.10.patch \
                   file://server/0004-gstreamer-encoder-Use-an-env-var-to-override-convert.patch \
                   file://server/0005-red-qxl-remove-cookie-assertion-on-scanout.patch \
                   file://server/0006-gstreamer-encoder-Use-a-h-w-based-encoder-with-Intel.patch \
                   file://server/0007-dcc-Check-to-see-if-the-client-supports-multiple-cod.patch \
                   file://server/0008-dcc-Create-a-stream-associated-with-gl_draw-for-non-.patch \
                   file://server/0009-dcc-send-Encode-and-send-gl_draw-stream-data-to-the-.patch \
                   file://server/0010-gstreamer-encoder-Add-an-encoder-function-that-takes.patch \
                   file://server/0011-video-stream-Don-t-stop-a-stream-associated-with-gl_.patch \
                   file://server/0012-gstreamer-encoder-Map-the-drm-format-to-appropriate-.patch \
                 "

do_patch:prepend() {
    bb.build.exec_func('do_fix_spice_common', d)
}
do_fix_spice_common() {
        cd ${S}/subprojects/spice-common && git checkout ${SRCREV_COMMON}
        cd ${S} && git add subprojects/spice-common && git commit -m "update spice-common"
}

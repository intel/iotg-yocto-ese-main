FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0010-Fix-X11-render.patch \
                   file://0011-Fix-Wayland-render.patch \
                   file://0001-Added-cmake-install-for-metrics-monitor-tools.patch \
                   file://0001-Allow-SMT-exit-with-no-encode-codec-is-set.patch \
                   file://0001-Fix-green-output-for-Y416-format.patch \
                 "

do_install:append() {
    mv ${D}${bindir}/metrics_monitor ${D}${datadir}/oneVPL/samples
}

FILES:${PN} += "${libdir}/libcttmetrics.so"

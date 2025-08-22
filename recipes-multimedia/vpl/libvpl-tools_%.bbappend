FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0003-Remove-redundant-fd-close.patch \
                   file://0004-Force-rDRM-setmode-highest-resolution-for-above-5k.patch \
                   file://0003-Remove-Y210-YUV-writer-shift.patch \
                 "

PACKAGECONFIG:append = " gtk4"
PACKAGECONFIG[gtk4] = "-DENABLE_GTK4=ON, -DENABLE_GTK4=OFF, gtkmm4"
S = "${WORKDIR}/git"


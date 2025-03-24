FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Enable-AV1-encode-Screen-Content-Coding-tools.patch \
                   file://0002-Implement-GTK-using-OpenGL-in-SD-and-SMT.patch \
                   file://0003-Remove-redundant-fd-close.patch \
                   file://0004-Force-rDRM-setmode-highest-resolution-for-above-5k.patch \
                 "

PACKAGECONFIG:append = " gtk4"
PACKAGECONFIG[gtk4] = "-DENABLE_GTK4=ON, -DENABLE_GTK4=OFF, gtkmm4"

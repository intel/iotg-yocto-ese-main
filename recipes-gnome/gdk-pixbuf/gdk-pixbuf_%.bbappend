# for icewm
GDK_PIXBUF_LOADERS:append = "${@bb.utils.contains('DISTRO_FEATURES', 'x11',' x11', '', d)}"

# for icewm
GDK_PIXBUF_LOADERS_append = "${@bb.utils.contains('DISTRO_FEATURES', 'x11',' x11', '', d)}"

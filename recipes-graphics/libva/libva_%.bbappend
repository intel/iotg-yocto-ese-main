FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
        file://0001-wayland-don-t-try-to-authenticate-with-render-nodes.patch \
        "

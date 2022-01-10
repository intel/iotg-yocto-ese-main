FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
    file://wayland-protocols/0001-hdr-metadata-Add-protocol-for-static-HDR-metadata.patch \
    file://wayland-protocols/0002-colorspace-Add-protocol-for-setting-surface-s-colors.patch \
"

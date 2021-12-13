FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
# remove build time rpath
SRC_URI:append = " file://remove-libstdc-vtv-rpath.patch"

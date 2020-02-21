FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
# remove build time rpath
SRC_URI_append = " file://remove-libstdc-vtv-rpath.patch"

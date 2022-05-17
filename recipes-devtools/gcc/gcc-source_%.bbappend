FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
# remove build time rpath
SRC_URI:append = " file://remove-libstdc-vtv-rpath.patch \
                   file://0001-libbacktrace-configure-backport-patch-to-enable_cet.patch \
                   file://0002-libiberty-fix-for-unrecognized-command-line-option.patch \
                 "

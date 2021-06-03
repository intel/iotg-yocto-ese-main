FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
# to avoid cet enable build failure in ubuntu16/18 with gcc version < 9.3.0
SRC_URI_append = " file://0001-config-cet.m4-backporting-changes-to-support-in-ese-.patch \
        file://0002-libbacktrace-configure-backport-patch-to-enable_cet.patch \
        file://0003-enable_cet-backport-patches-for-enable_cet.patch \
        file://0004-libiberty-cet-enable-patch-update.patch \
        file://0005-libiberty-fix-for-unrecognized-command-line-option.patch \
"

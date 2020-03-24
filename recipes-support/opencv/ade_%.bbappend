FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
                   file://0001-use-GNUInstallDirs-for-detecting-install-paths.patch \
                 "

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Spawn extra worker threads
SRC_URI += " \
             file://0024-more-udev-children-workers.patch \
           "

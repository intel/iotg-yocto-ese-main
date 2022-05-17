FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Spawn extra worker threads
SRC_URI += " \
             file://0001-more-udev-children-workers.patch \
           "

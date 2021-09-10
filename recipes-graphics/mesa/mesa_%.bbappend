require mesa_git.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
        file://0001-iris-Add-renderonly-support.patch \
        file://0002-kmsro-Add-iris-renderonly-support.patch \
        file://0003-iris-kmsro-use-ro-device-to-allocate-scanout-for-ren.patch \
        file://0004-Revert-isl-Work-around-NVIDIA-and-AMD-display-pitch-.patch \
"

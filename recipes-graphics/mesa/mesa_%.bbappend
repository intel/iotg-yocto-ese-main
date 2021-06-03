require mesa_git.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
        file://0001-intel-dev-Add-device-info-for-ADL-GT2.patch \
        file://0002-intel-isl-Add-WA-to-disable-CCS-on-ADL-GT2-A0.patch \
        file://0003-isl-Round-row-pitch-to-a-power-of-two-for-ADL-GT2.patch \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://0001-dri2-Add-support-for-TGL.patch \
                   file://0002-Imported-the-ADL-S-GFX-device-id-to-the-xorg.patch \
                   file://0003-xorg-xserver-Add-in-ADL-P-support.patch \
                   file://0004-xorg-xserver-Add-support-for-ADL-N.patch \
                   file://0005-xorg-xserver-Add-support-for-RPL-S.patch \
                   file://0006-xorg-xserver-Add-support-for-RPL-P.patch \
                 "


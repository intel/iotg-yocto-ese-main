DESCRIPTION = "ese-installer initramfs"

IMAGE_FEATURES_append = " read-only-rootfs"
IMAGE_FEATURES_remove = "ssh-server-openssh"
IMAGE_LINGUAS = ""
LICENSE = "MIT"
IMAGE_FSTYPES = "cpio"

inherit image eudev-initramfs

python() {
    d.setVar("KERNELDEPMODDEPEND","")
}

PACKAGE_INSTALL = "ese-installer-init"
do_image[recrdeptask] += "ese-installer-init:build"

DESCRIPTION = "iscsi init helper image"
PACKAGE_INSTALL = "netboot-init"
IMAGE_FEATURES:append = " read-only-rootfs"
IMAGE_FEATURES:remove = "ssh-server-openssh"
IMAGE_LINGUAS = ""
LICENSE = "MIT"
IMAGE_FSTYPES = "cpio cpio.lz4"

inherit image eudev-initramfs

python() {
    d.setVar("KERNELDEPMODDEPEND","")
}

# needs to be embedded into a signed kernel
INCOMPATIBLE_LICENSE = "GPL-3.0 LGPL-3.0 AGPL-3.0"

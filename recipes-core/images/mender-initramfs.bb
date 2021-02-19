DESCRIPTION = "Mender init helper image"
PACKAGE_INSTALL = "mender-init"
DISTRO_FEATURES_remove = "mender-install mender-image mender-grub mender-systemd"
IMAGE_FEATURES_append = " read-only-rootfs"
IMAGE_FEATURES_remove = "ssh-server-openssh"
IMAGE_LINGUAS = ""
LICENSE = "MIT"
IMAGE_FSTYPES = "cpio"

require eudev-initramfs.inc
inherit image

python() {
    d.setVar("KERNELDEPMODDEPEND","")
}

# needs to be embedded into a signed kernel
INCOMPATIBLE_LICENSE = "GPL-3.0 LGPL-3.0 AGPL-3.0"

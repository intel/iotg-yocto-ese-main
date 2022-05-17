DESCRIPTION = "Mender init helper image"
PACKAGE_INSTALL = "mender-init"
MENDER_FEATURES_DISABLE:append = "mender-client-install mender-image mender-grub mender-systemd"
IMAGE_FEATURES:append = " read-only-rootfs"
IMAGE_FEATURES:remove = "ssh-server-openssh"
IMAGE_LINGUAS = ""
LICENSE = "MIT"
IMAGE_FSTYPES = "cpio"

inherit eudev-initramfs image

python() {
    d.setVar("KERNELDEPMODDEPEND","")
}

# needs to be embedded into a signed kernel
INCOMPATIBLE_LICENSE = "GPL-3.0-only LGPL-3.0-only AGPL-3.0-only"

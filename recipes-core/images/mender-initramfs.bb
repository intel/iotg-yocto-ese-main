DESCRIPTION = "Mender init helper image"
PACKAGE_INSTALL = "mender-init"
DISTRO_FEATURES_remove = "mender-install mender-image mender-grub mender-systemd"
IMAGE_FEATURES_append = " read-only-rootfs"
IMAGE_FEATURES_remove = "ssh-server-openssh"
IMAGE_LINGUAS = ""
LICENSE = "MIT"
IMAGE_FSTYPES = "cpio"

inherit image

python() {
    d.setVar("KERNELDEPMODDEPEND","")
}

# for eudev
inherit extrausers
EXTRA_USERS_PARAMS = "\
  groupadd -g 5 tty; \
  groupadd -g 6 disk; \
  groupadd -g 7 lp; \
  groupadd -g 9 kmem; \
  groupadd -g 18 audio; \
  groupadd -g 19 cdrom; \
  groupadd -g 20 dialout; \
  groupadd -g 27 video; \
  groupadd -g 78 kvm; \
  groupadd -g 249 input; \
"

# needs to be embedded into a signed kernel
INCOMPATIBLE_LICENSE = "GPL-3.0 LGPL-3.0 AGPL-3.0"

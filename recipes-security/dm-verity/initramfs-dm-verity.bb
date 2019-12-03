SUMMARY = "Initramfs image"
DESCRIPTION = "Supports dm-verity."
LICENSE = "MIT"

# Reference:
# https://github.com/intel/intel-iot-refkit/blob/master/meta-refkit-core/recipes-images/images/refkit-initramfs.bb

inherit core-image

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

# initramfs-framework-dm-verity: dm-verity tasks performed by initramfs
# busybox: Linux shell and other basic utilities needed by initramfs
# initramfs-module-udev: Used to detect boot devices automatically
# lvm2: Used by udev daemon for udev rules and other dependencies
PACKAGE_INSTALL = "initramfs-framework-dm-verity initramfs-module-data busybox initramfs-module-udev lvm2"

# busybox-udhcpc busybox-udhcpd busybox-dbg busybox-syslog are not needed,
# unless bitbake finds dependencies on these packages
BAD_RECOMMENDATIONS += "busybox-udhcpc busybox-udhcpd busybox-dbg busybox-syslog"

IMAGE_INSTALL_remove = "mender"
DISTRO_FEATURES_remove = "mender-install mender-uboot"

IMAGE_FEATURES_remove = "dev-pkgs debug-tweaks package-management"

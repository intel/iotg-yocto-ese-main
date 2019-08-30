# This file allows for setting variables that can be shared by multiple recipes (if any).

# Reference:
# https://github.com/intel/intel-iot-refkit/blob/master/meta-refkit-core/recipes-images/images/refkit-boot-settings.inc

# Determines what name is used under /dev/mapper/ for the rootfs.
DEVICE_MAPPER_ROOTFS_NAME ??= "rootfs"

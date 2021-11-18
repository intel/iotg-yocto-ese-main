IMAGE_CMD_squashfs-zstd = "mksquashfs ${IMAGE_ROOTFS} ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.squashfs-zstd ${EXTRA_IMAGECMD} -noappend -comp zstd"
do_image_squashfs_zstd[depends] += "squashfs-tools-native:do_populate_sysroot"
IMAGE_TYPES_append = " squashfs-zstd"

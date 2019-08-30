# decouples kernel modules and bzImage depedencies
RDEPENDS_${KERNEL_PACKAGE_NAME}-base_remove = "${KERNEL_PACKAGE_NAME}-image"

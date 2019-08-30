# Misc fixes
DEPENDS += "coreutils-native"
FILES_${KERNEL_PACKAGE_NAME}-base_append = " ${nonarch_base_libdir}/modules/${KERNEL_VERSION}/modules.builtin.modinfo"

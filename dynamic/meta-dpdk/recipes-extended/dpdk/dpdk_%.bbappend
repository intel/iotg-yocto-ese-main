# unfortunately upstream maintainer refuses to split
# recipe into userspace and kernel space respectively
# workaround for multi kernel out of tree module support

# we may not be able to predict the package names otherwise
PACKAGESPLITFUNCS_remove="split_kernel_module_packages"

# do not strip modules, unfortunately also applied to userspace
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "intel-(corei7|skylake)-64"
PACKAGECONFIG_append_class-target = " numa libvirt vhost"

PACKAGES += "${PN}-modules"
FILES_${PN}-modules = "${nonarch_base_libdir}/modules \
 ${sysconfdir}/modprobe.d \
 ${sysconfdir}/modules-load.d \
"

### userspace utilities still being installed into image and
# causing conflicts despite no explicit dependency links between
# kernel modules and userspace

do_install_append(){
	if [ "${KERNEL_PACKAGE_NAME}" != "kernel" ]; then
		rm -rf ${D}/${prefix}
	fi
}

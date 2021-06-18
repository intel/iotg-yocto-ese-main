# unfortunately upstream maintainer refuses to split
# recipe into userspace and kernel space respectively
# workaround for multi kernel out of tree module support

# we may not be able to predict the package names otherwise
PACKAGESPLITFUNCS_remove="split_kernel_module_packages"

# do not strip modules, unfortunately also applied to userspace
INHIBIT_PACKAGE_STRIP = "1"

COMPATIBLE_MACHINE = "intel-(corei7|skylake)-64"
PACKAGECONFIG_append_class-target = " numa libvirt vhost"

# files moved from ${PN}-modules -> ${PN}
# userspace contents now in ${BPN}-userspace

PACKAGES += "${PN}-modules ${PN}-userspace"
FILES_${PN}-modules = ""
ALLOW_EMPTY_${PN}-modules = "1"
RDEPENDS_${PN}-modules = "${PN} ${PN}-userspace"

FILES_${PN} = "${nonarch_base_libdir}/modules"
FILES_${PN}-userspace = " \
 ${sysconfdir}/modprobe.d \
 ${sysconfdir}/modules-load.d \
 ${prefix} \
"
# -userspace is empty if non-default kernel
ALLOW_EMPTY_${PN}-userspace = "1"

### userspace utilities still being installed into image and
# causing conflicts despite no explicit dependency links between
# kernel modules and userspace

do_install_append(){
	if [ "${KERNEL_PACKAGE_NAME}" != "kernel" ]; then
		rm -rf ${D}/${prefix}
	fi
}

# try to workaround old meta layer configurations that use "dpdk" directly
# instead of using <kernel recipe>-dpdk
RREPLACES_${PREFERRED_PROVIDER_virtual/kernel}-dpdk = "dpdk"
RREPLACES_${PREFERRED_PROVIDER_virtual/kernel}-dpdk-modules = "dpdk-modules"
RREPLACES_${PREFERRED_PROVIDER_virtual/kernel}-dpdk-userspace = "dpdk-userspace"

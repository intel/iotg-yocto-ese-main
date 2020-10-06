DESCRIPTION = "Installation imager helper recipe"
LICENSE = "MIT"
inherit deploy allarch

INHIBIT_DEFAULT_DEPS = "1"

# to mate recrdeptask and mcdepends
do_build_image(){
	if test -z "${ESE_INSTALLER_ROOTFS_MCDEPENDS}"; then
		echo "ESE_INSTALLER_ROOTFS_MCDEPENDS is unset!"
		false
	fi
}

do_deploy(){
	if test -f "${ESE_INSTALLER_ROOTFS}"; then
		ln -sf "${ESE_INSTALLER_ROOTFS}" ${DEPLOYDIR}/${BPN}.img
	else
		echo "ESE_INSTALLER_ROOTFS is unset!"
		false
	fi
}

addtask do_build_image
do_build_image[mcdepends] = "${ESE_INSTALLER_ROOTFS_MCDEPENDS}"
do_deploy[recrdeptask] += "do_build_image"
addtask deploy after do_build_image before do_build

## to be overriden by user, since bitbake can't guess where it is
## we are interested in the mender-mangled post processed image
## Example:
#ESE_INSTALLER_ROOTFS ??= "${TOPDIR}/tmp-x86-glibc/deploy/images/intel-corei7-64/core-image-sato-sdk-intel-corei7-64.squashfs-lz4"
#ESE_INSTALLER_ROOTFS_MCDEPENDS ??= "mc:${BB_CURRENT_MC}:x86:core-image-sato-sdk:do_image_complete"

require recipes-kernel/linux/linux-yocto.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-config:"

LINUX_VERSION_EXTENSION = "-intel-${LINUX_KERNEL_TYPE}"
PV = "${LINUX_VERSION}+git${SRCPV}"

DEPENDS += "util-linux-native openssl-native elfutils-native lz4-native"

# To fix do_configure issue
do_kernel_configme[depends] += "${PN}:do_prepare_recipe_sysroot"

BSPSUBTYPE_x86-64 = "x86"
BSP_SUBTYPE ??= "${BSPSUBTYPE}"

##########
# x86_64
##########

SRC_URI_append_x86-64 = \
" file://bsp/${BSP_SUBTYPE}/defconfig \
  file://bsp/${BSP_SUBTYPE}/intel-dev.scc \
"

# qemu virtio guest
SRC_URI_append_x86-64 = " file://bsp/${BSP_SUBTYPE}/virtio-guest.scc"

# Optional kernel security harderning that may interfere with debugging
SRC_URI_append_x86-64 = " ${@bb.utils.contains('DISTRO_FEATURES', 'hardened', 'file://bsp/${BSP_SUBTYPE}/security.scc', '', d)}"
DEPENDS_append_x86-64 = " ${@bb.utils.contains('DISTRO_FEATURES', 'hardened', 'openssl-native', '', d)}"
INHIBIT_PACKAGE_STRIP = "1"

# are we using backport-iwlwifi?
SRC_URI_append_x86-64 = " ${@bb.utils.contains("DISTRO_FEATURES","backport-iwlwifi", "file://bsp/${BSP_SUBTYPE}/no-wifi.cfg", "file://bsp/${BSP_SUBTYPE}/yes-wifi.cfg", d)}"

# do we want TPM?
SRC_URI_append_x86-64 = " ${@bb.utils.contains("MACHINE_FEATURES","tpm", "file://bsp/${BSP_SUBTYPE}/yes-tpm.cfg", "file://bsp/${BSP_SUBTYPE}/no-tpm.cfg", d)}"

# TSN patches for v5.3 kernel for TGL
SRC_URI_append_x86-64 = " file://bsp/${BSP_SUBTYPE}/ethernet-patches.scc"

#########
# common
#########

SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'dm-verity', 'file://dmverity.cfg', '', d)}"
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'smack', 'file://lsm-stacking.cfg', '', d)}"
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'lsm-debug', 'file://lsm-debug.cfg', '', d)}"

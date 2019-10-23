KERNEL_SRC_URI ?= "git://github.com/intel/mainline-tracking.git;protocol=https;nobranch=1;name=machine"
SRC_URI = "${KERNEL_SRC_URI}"
SRCREV_machine ?= "1009f203ed3132446231caa90353aa8701f0d86e"
LINUX_VERSION ?= "5.3"
LINUX_KERNEL_TYPE = "mainline-tracking-stable"
KERNEL_PACKAGE_NAME = "${PN}-kernel"

require recipes-kernel/linux/linux-intel-ese.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

# Optional kernel security harderning that may interfere with debugging
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'hardened', 'file://bsp/${BSP_SUBTYPE}/security.scc', '', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'hardened', 'openssl-native', '', d)}"
INHIBIT_PACKAGE_STRIP = "${@bb.utils.contains('DISTRO_FEATURES', 'hardened', '1', '0', d)}"

# fix build warnings caused by libbpf
SRC_URI_append = " file://libbpf/37b7c058d4103b7ea8fb3773d330a883c0f66592..dadb81d0afe732a7670f7c1bd287dada163a9f2f.patch"
SRC_URI_append = " file://libbpf/0001-Added-ldflags.patch"
SRC_URI_append = " file://libbpf.scc"

# add patch for ethernet
SRC_URI_append = " file://ethernet.scc"

# add patch for audio
SRC_URI_append = " file://audio.scc"

# Enable tgl pinctrl
SRC_URI_append = " file://pinctrl.scc"

# i915 video PCI ID
SRC_URI_append = " file://graphics-5.3.scc"

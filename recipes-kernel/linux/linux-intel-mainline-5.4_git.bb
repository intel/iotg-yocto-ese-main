KERNEL_SRC_URI ?= "git://github.com/intel/mainline-tracking.git;protocol=https;nobranch=1;name=machine"
SRC_URI = "${KERNEL_SRC_URI}"
SRCREV_machine ?= "bcb166b53a3b0d16b03cb33daff9b94820644c4b"
LINUX_VERSION ?= "5.4"
LINUX_KERNEL_TYPE = "mainline-tracking"
KERNEL_PACKAGE_NAME = "${PN}-kernel"

require recipes-kernel/linux/linux-intel-ese.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

# Optional kernel security harderning that may interfere with debugging
SRC_URI_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'hardened', 'file://bsp/${BSP_SUBTYPE}/security.scc', '', d)}"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'hardened', 'openssl-native', '', d)}"
INHIBIT_PACKAGE_STRIP = "${@bb.utils.contains('DISTRO_FEATURES', 'hardened', '1', '0', d)}"

# fix build warnings caused by libbpf
SRC_URI_append = " file://libbpf/0001-Added-ldflags.patch"

# add patch for ethernet
SRC_URI_append = " file://ethernet.scc"

# add patch for audio
SRC_URI_append = " file://audio.scc"

# resctrl patches for 5.4
SRC_URI_append = " file://resctrl-5.4.scc"

# Programmable Software Engine
SRC_URI_append = " file://ishtp-5.4.scc"

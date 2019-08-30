SUMMARY = "gPTP daemon"
DESCRIPTION = "gPTP daemon is used to establish clock syncrhonization. Daemon will create shared memory segment with the "ptp" group."
LICENSE = "BSD-3-Clause"

SRC_URI = "git://github.com/AVnu/gptp.git;protocol=https;branch=master"

LIC_FILES_CHKSUM = "file://${S}/linux/src/daemon_cl.cpp;beginline=3;endline=30;md5=5b64817b6a24d68d79b26402a9aef686"

SRCREV = "9cfa1b6f1f68d4fe52abdd973a8b6e31d7f67d1c"
S = "${WORKDIR}/git"
PV = "2.0-git${SRCPV}"

SRC_URI[md5sum] = "299b8ea2a1a7c6833fb65501ed2ef6bb"
SRC_URI[sha256sum] = "30a3553aaf576033611f7aedae9f082ac76c60ddb2ca3edf322678ca383aaa29"

DEPENDS += "pciutils"

CFLAGS_append = " -fPIC"

do_configure[noexec] = "1"

do_compile() {
    oe_runmake -C linux/build all
}

do_install() {
    install -d ${D}${includedir}
    install -m 644 ${B}/common/ipcdef.hpp ${D}${includedir}
    install -m 644 ${B}/common/ptptypes.hpp ${D}${includedir}
    install -m 644 ${B}/linux/src/linux_ipc.hpp ${D}${includedir}

    install -d ${D}${bindir}
    install -m 755 linux/build/obj/daemon_cl ${D}${bindir}
}

FILES_${PN} += "${bindir}"
INSANE_SKIP_${PN} = "ldflags"

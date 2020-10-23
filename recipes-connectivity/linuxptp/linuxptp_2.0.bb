DESCRIPTION = "Precision Time Protocol (PTP) according to IEEE standard 1588 for Linux"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "\
           git://git.code.sf.net/p/linuxptp/code;protocol=https;branch=master \
           file://build-Allow-CC-and-prefix-to-be-overriden.patch \
           file://no-incdefs-using-host-headers.patch \
           "

SRCREV = "c15e8c76000cdd00335a039f89693b820044ac91"
PV = "2.0+git${SRCPV}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "ARCH=${TARGET_ARCH} \
    EXTRA_CFLAGS='-D_GNU_SOURCE -DHAVE_CLOCK_ADJTIME -DHAVE_POSIX_SPAWN -DHAVE_ONESTEP_SYNC ${CFLAGS}'"

do_install(){
	oe_runmake DESTDIR=${D} mandir=${mandir} install
}

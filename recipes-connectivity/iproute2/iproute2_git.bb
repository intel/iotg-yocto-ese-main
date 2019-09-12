require recipes-connectivity/iproute2/iproute2.inc

SRC_URI = "git://git.kernel.org/pub/scm/network/iproute2/iproute2-next.git"
SRCREV= "a794d0523711d5ab4530483b9435ba627e07d28b"
S = "${WORKDIR}/git"
PV = "git+${SRCPV}"

# CFLAGS are computed in Makefile and reference CCOPTS
#
EXTRA_OEMAKE_append = " CCOPTS='${CFLAGS}'"


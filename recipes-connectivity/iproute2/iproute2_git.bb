require recipes-connectivity/iproute2/iproute2.inc

SRC_URI = "git://git.kernel.org/pub/scm/network/iproute2/iproute2-next.git \
           file://0001-taprio-Add-support-for-setting-Frame-Preemption-Queu.patch \
           file://0001-tc-taprio-increase-the-sched-entry-msg-size.patch \
"
SRCREV= "a8360dd3f25fc8fe4730ab39a8d7b359f397149f"
S = "${WORKDIR}/git"
PV = "git+${SRCPV}"

# CFLAGS are computed in Makefile and reference CCOPTS
#
EXTRA_OEMAKE_append = " CCOPTS='${CFLAGS}'"


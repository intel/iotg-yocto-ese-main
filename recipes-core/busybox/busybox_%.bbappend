FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://disable-syslogd-klogd.cfg \
            file://misc.cfg \
            file://procps.cfg \
           "

SYSTEMD_PACKAGES_remove = "${PN}-syslog"
INITSCRIPT_PACKAGES_remove = "${PN}-syslog"
RRECOMMENDS_${PN}_remove = " ${PN}-syslog"

# Emergency Linux Kernel
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'elk-busybox', 'file://elk.cfg', '', d)}"

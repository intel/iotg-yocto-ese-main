FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://disable-syslogd-klogd.cfg \
            file://misc.cfg \
            file://procps.cfg \
            file://ipv6.cfg \
           "

SYSTEMD_PACKAGES_remove = "${PN}-syslog"
INITSCRIPT_PACKAGES_remove = "${PN}-syslog"
RRECOMMENDS_${PN}_remove = " ${PN}-syslog"

# Emergency Linux Kernel
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'elk-busybox', 'file://elk.cfg', '', d)}"

SRC_URI_append_ese-installer-overrides = " file://enable-init.cfg file://ese-banner.inc"
TARGET_CFLAGS_append_ese-installer-overrides = " -DCUSTOMIZED_BANNER=\\"${WORKDIR}/ese-banner.inc\\""

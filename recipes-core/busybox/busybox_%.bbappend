FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://disable-syslogd-klogd.cfg \
            file://misc.cfg \
            file://procps.cfg \
            file://ipv6.cfg \
           "

SYSTEMD_PACKAGES:remove = "${PN}-syslog"
INITSCRIPT_PACKAGES:remove = "${PN}-syslog"
RRECOMMENDS:${PN}:remove = " ${PN}-syslog"

# Emergency Linux Kernel
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'elk-busybox', 'file://elk.cfg', '', d)}"

SRC_URI:append_ese-installer-overrides = " file://enable-init.cfg file://ese-banner.inc"
TARGET_CFLAGS:append_ese-installer-overrides = " -DCUSTOMIZED_BANNER=\\"${WORKDIR}/ese-banner.inc\\""

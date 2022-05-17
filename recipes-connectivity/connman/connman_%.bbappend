# This is the WA for gatesgarth to run connman.service
# Added due to /etc/resolv.conf is missing when connman.service is not enabled

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0002-connman-stop-dhcpcd-service-when-connman-starts.patch"

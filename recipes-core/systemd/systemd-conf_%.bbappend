# this prevents systemd from dropping addresses in preconfigured interfaces
# used to access the root filesystem over the network
FILESEXTRAPATHS:prepend_ese-netboot-overrides := "${THISDIR}/${PN}:"
SRC_URI:append_ese-netboot-overrides = " file://wired.patch;patchdir=${WORKDIR}"

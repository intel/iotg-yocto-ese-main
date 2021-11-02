FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI:append = "file://xserver-nodm.path"

# gates xserver from starting unless graphics DRI interfaces are up
# modify as required for /dev/fb*
SYSTEMD_SERVICE:${PN} = "xserver-nodm.path"

do_install:append(){
	install -m 0644 ${WORKDIR}/xserver-nodm.path ${D}${systemd_unitdir}/system/xserver-nodm.path
}

FILES:${PN} += "${systemd_unitdir}/system"

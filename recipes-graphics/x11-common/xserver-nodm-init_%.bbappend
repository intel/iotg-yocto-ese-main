FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = "file://xserver-nodm.path"

# gates xserver from starting unless graphics DRI interfaces are up
# modify as required for /dev/fb*
SYSTEMD_SERVICE_${PN} = "xserver-nodm.path"

do_install_append(){
	install -m 0644 ${WORKDIR}/xserver-nodm.path ${D}${systemd_unitdir}/system/xserver-nodm.path
}

FILES_${PN} += "${systemd_unitdir}/system"

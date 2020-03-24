inherit deploy

do_deploy() {
	mkdir -p ${DEPLOYDIR}/${PN}/${GRUB_CONF_LOCATION}
	cp -r ${D}${GRUB_CONF_LOCATION}/mender_grubenv1 ${DEPLOYDIR}/${PN}/${GRUB_CONF_LOCATION}
	cp -r ${D}${GRUB_CONF_LOCATION}/mender_grubenv2 ${DEPLOYDIR}/${PN}/${GRUB_CONF_LOCATION}
}

addtask deploy before do_build after do_install

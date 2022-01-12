inherit deploy

do_deploy() {
	mkdir -p ${DEPLOYDIR}/${PN}/${GRUB_CONF_LOCATION}
	cp -r ${D}${GRUB_CONF_LOCATION}/mender_grubenv1 ${DEPLOYDIR}/${PN}/${GRUB_CONF_LOCATION}
	cp -r ${D}${GRUB_CONF_LOCATION}/mender_grubenv2 ${DEPLOYDIR}/${PN}/${GRUB_CONF_LOCATION}
}

addtask deploy before do_build after do_install

# do not provide if another provider is used
do_install:append(){
	if test "${PREFERRED_PROVIDER_virtual/grub-bootconf}" != "${PN}"; then
		rm -f "${D}${GRUB_CONF_LOCATION}/grub.cfg"
	fi
}

python(){
  import re
  pn = d.getVar('PN')
  pref = d.getVar('PREFERRED_PROVIDER_virtual/grub-bootconf')

  if pref != pn:
    rprov = d.getVar('RPROVIDES:' + pn)
    re.sub('virtual/grub-bootconf', '', rprov)
    d.setVar('RPROVIDES:' + pn, rprov)
}

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
    # Remove 'virtual/grub-bootconf' from RPROVIDES
    rprov = d.getVar('RPROVIDES:' + pn)
    subrprov = re.sub('virtual/grub-bootconf', '', rprov)
    d.setVar('RPROVIDES:' + pn, subrprov)
    # Remove 'virtual/grub-bootconf' from PROVIDES
    prov = d.getVar('PROVIDES')
    subprov = re.sub('virtual/grub-bootconf', '', prov)
    d.setVar('PROVIDES', subprov)
}

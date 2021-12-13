do_install:append(){
	if test -f ${S}/phc_ctl; then
		install -m 755 ${S}/phc_ctl ${D}/${bindir}
	fi
}


do_install_append(){
        install -m 755 ${S}/phc_ctl ${D}/${bindir}
}


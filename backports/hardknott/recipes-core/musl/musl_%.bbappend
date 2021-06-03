#fix compilation issues on hardknott

do_install_prepend(){
	install -d ${D}/${base_libdir}
}

#fix compilation issues on hardknott

do_install:prepend(){
	install -d ${D}/${base_libdir}
}

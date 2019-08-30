# perllocal.pod should not be installed
remove_perllocal(){
	if [ -d "${D}/${libdir}/perl5" ]; then
		find "${D}/${libdir}/perl5" -type f -name perllocal.pod -delete
	fi
}

do_install[postfuncs] += "remove_perllocal"

#libdir fixup
do_install_append(){
	if test "${baselib}" != lib; then
		mv ${D}/${prefix}/lib/libcl* "${D}/${libdir}"
		# break if not empty
		rm -r "${D}/${prefix}/lib"
	fi
}

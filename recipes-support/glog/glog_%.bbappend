DEPENDS += "gflags"

# Hack to get around hard coded install path
do_install_append() {
	if [ "(" "${libdir}" != "${prefix}/lib" ")" -a \
		-d "${D}${prefix}/lib" -a ! -d "${D}${libdir}" ]; then
		mv "${D}${prefix}/lib" "${D}${libdir}"
	fi
}

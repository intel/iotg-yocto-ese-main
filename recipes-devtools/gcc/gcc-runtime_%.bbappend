PACKAGES_append = " libvtv libvtv-staticdev libvtv-dev"
FILES_libvtv = "${libdir}/libvtv.so.0*"
FILES_libvtv-staticdev = "${libdir}/libvtv.a"
FILES_libvtv-dev = "${libdir}/libvtv.so ${libdir}/${TARGET_SYS}/${BINV}/include/vtv_*.h"
RRECOMMENDS_libstdc++_append = " libvtv"
RRECOMMENDS_libstdc++-dev_append = " libvtv-dev"
RRECOMMENDS_libstdc++-staticdev_append = " libvtv-staticdev"

# fix libvtv using a different variable for calling gcc
do_compile_prepend(){
	export CC_FOR_TARGET="${CXX} -nostdinc++ -nostdlib++"
}

PACKAGES:append = " libvtv libvtv-staticdev libvtv-dev"
FILES:libvtv = "${libdir}/libvtv.so.0*"
FILES:libvtv-staticdev = "${libdir}/libvtv.a"
FILES:libvtv-dev = "${libdir}/libvtv.so ${libdir}/${TARGET_SYS}/${BINV}/include/vtv_*.h"
RRECOMMENDS:libstdc++_append = " libvtv"
RRECOMMENDS:libstdc++-dev:append = " libvtv-dev"
RRECOMMENDS:libstdc++-staticdev:append = " libvtv-staticdev"

# fix libvtv using a different variable for calling gcc
do_compile:prepend(){
	export CC_FOR_TARGET="${CXX} -nostdinc++ -nostdlib++"
}

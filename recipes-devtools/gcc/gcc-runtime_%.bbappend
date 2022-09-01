PACKAGES:append = " libvtv libvtv-staticdev libvtv-dev"
FILES:libvtv = "${libdir}/libvtv.so.0*"
FILES:libvtv-staticdev = "${libdir}/libvtv.a"
FILES:libvtv-dev = "${libdir}/libvtv.so ${libdir}/${TARGET_SYS}/${BINV}/include/vtv_*.h"
RRECOMMENDS:libstdc++:append = " libvtv"
RRECOMMENDS:libstdc++-dev:append = " libvtv-dev"
RRECOMMENDS:libstdc++-staticdev:append = " libvtv-staticdev"

# fix libvtv do_package_qa issue: libvtv.so.0.0.0 requires libstdc++.so()(64bit)
# WA to revert openembedded-core, commit f842dbc478cb007b5f3e2f016959e2b597a4d0be
do_configure:append() {
	rm -rf ${WORKDIR}/dummylib/libstdc++.so
	touch ${WORKDIR}/dummylib/libstdc++.so
}

# fix libvtv using a different variable for calling gcc
do_compile:prepend() {
	export CC_FOR_TARGET="${CXX} -nostdinc++"
}

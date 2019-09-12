SUMMARY = "Intel® Single Event API"
LICENSE = "BSD-3-Clause | GPLv2"
LIC_FILES_CHKSUM = "file://sea_itt_lib/Copyright.txt;md5=7d54dfc8860742fb06b9c5ad28f41fcd"

SRC_URI = "\
	git://github.com/intel/IntelSEAPI.git;protocols=https \
	file://0001-CMakeLists.txt-remove-broken-install-path-assumption.patch \
"

SRCREV="f41831f9d576eadf48f517bd7d7002b17d6ef30f"
PV = "git+${SRCPV}"
S = "${WORKDIR}/git"
ALLOW_EMPTY_${PN} = "1"

inherit pkgconfig cmake pythonnative

# worksround broken cmake install path
do_install_append(){
	install -d ${D}/${datadir}/${PN}
	mv ${D}/${prefix}/runtool ${D}/${datadir}/${PN}/
	mv ${D}/${prefix}/README.txt ${D}/${datadir}/${PN}/
}

PACKAGES += "${PN}-runtool"
FILES_${PN}-runtool = "${datadir}/${PN}/runtool"
BBCLASSEXTEND += "nativesdk"

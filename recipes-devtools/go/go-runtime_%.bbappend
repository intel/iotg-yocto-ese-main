PACKAGES =+ "${PN}-cmd"

FILES_${PN}-cmd = "\
    ${libdir}/go/src/cmd/* \
"

RDEPENDS_${PN}-cmd = "bash"
RRECOMMENDS_${PN}-dev += "${PN}-cmd"
RCONFLICTS_${PN}-cmd += "go-dev"

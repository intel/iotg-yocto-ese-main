PACKAGES =+ "${PN}-cmd"

FILES:${PN}-cmd = "\
    ${libdir}/go/src/cmd/* \
"

RDEPENDS:${PN}-cmd = "bash"
RRECOMMENDS:${PN}-dev += "${PN}-cmd"
RCONFLICTS:${PN}-cmd += "go-dev"

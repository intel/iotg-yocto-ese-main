include ${BPN}.inc

SRC_URI = "\
    https://github.com/tpm2-software/${BPN}/releases/download/${PV}/${BPN}-${PV}.tar.gz \
"

SRC_URI[md5sum] = "701ae9e8c8cbdd37d89c8ad774f55395"
SRC_URI[sha256sum] = "40b9263d8b949bd2bc03a3cd60fa242e27116727467f9bbdd0b5f2539a25a7b1"

S = "${WORKDIR}/${BPN}-${PV}"

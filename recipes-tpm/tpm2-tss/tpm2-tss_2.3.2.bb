include ${BPN}.inc

SRC_URI = "https://github.com/tpm2-software/${BPN}/releases/download/${PV}/${BPN}-${PV}.tar.gz"

SRC_URI[md5sum] = "fb7e6d371959a65dc6d129af81739742"
SRC_URI[sha256sum] = "82929a0611f39246e09202702a61b54c980ab694626c1f5823520ddf75024fa6"

S = "${WORKDIR}/${BPN}-${PV}"

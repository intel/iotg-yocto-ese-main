include ${BPN}.inc

SRC_URI = "\
    https://github.com/tpm2-software/${BPN}/releases/download/${PV}/${BPN}-${PV}.tar.gz \
"
SRC_URI[md5sum] = "071aa40bc8721700ea4ed19cc2fdeabf"
SRC_URI[sha256sum] = "ccec3fca6370341a102c5c2ef1ddb4e5cd242bf1bbc6c51d969f77fc78ca67d1"

S = "${WORKDIR}/${BPN}-${PV}"

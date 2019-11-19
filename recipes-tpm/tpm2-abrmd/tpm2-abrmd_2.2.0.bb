include ${BPN}.inc

SRC_URI += " \
    https://github.com/tpm2-software/${BPN}/releases/download/${PV}/${BPN}-${PV}.tar.gz \
    file://tpm2-abrmd.default \
"
SRC_URI[md5sum] = "9ef120a84d8435568ffe542d4ec30359"
SRC_URI[sha256sum] = "2a75a09802290d88f7806d1294b0070f92dd3f138a211834caa4cc04bd876ed1"

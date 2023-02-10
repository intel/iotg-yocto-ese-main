FILESEXTRAPATHS:prepend := "${THISDIR}/libbpf:"

SRC_URI:append = " file://0001-libbpf-add-txtime-field-in-xdp_desc-struct.patch;patchdir=${S}/../"


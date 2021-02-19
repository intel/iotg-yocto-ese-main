FILESEXTRAPATHS_prepend_libc-musl := "${THISDIR}/files:"
SRC_URI_append_libc-musl = " file://test.patch"

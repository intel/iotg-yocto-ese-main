FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " ${@bb.utils.contains('PV', '3.5.0', 'file://0001-libnl-KW-issue-fixes.patch', '', d)}"

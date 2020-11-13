FILESEXTRAPATHS_prepend := "${THISDIR}/hostapd:"

SRC_URI_append = " \ 
		file://0001-hostapd-KW-issues-changes.patch \
		file://defconfig \
		"

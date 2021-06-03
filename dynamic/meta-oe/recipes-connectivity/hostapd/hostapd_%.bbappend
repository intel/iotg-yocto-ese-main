FILESEXTRAPATHS_prepend := "${THISDIR}/hostapd:"

SRC_URI_append = " \ 
		file://defconfig \
		"

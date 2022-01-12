FILESEXTRAPATHS:prepend := "${THISDIR}/hostapd:"

SRC_URI:append = " \ 
		file://defconfig \
		"

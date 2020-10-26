FILESEXTRAPATHS_prepend := "${THISDIR}/wpa-supplicant:"

SRC_URI_append = " \ 
	   file://wpa-supplicant-Handle-long-P2P-Device-Interface-name.patch \
	   file://0001-wpa_supplicant-KW-issues-changes.patch \
          "

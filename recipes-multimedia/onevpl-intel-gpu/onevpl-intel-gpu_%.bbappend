FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-onevpl-intel-gpu-Enable-RPL-S-support.patch \
		   file://0002-onevpl-intel-gpu-Enable-ADL-N-support.patch \
		   file://0003-onevpl-intel-gpu-RPL-P-enablement.patch \
                 "

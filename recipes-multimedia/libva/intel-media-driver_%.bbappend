FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Force-disable-MMC-and-scalability-on-will-caused-GPU.patch \
		   file://0002-Revert-mos-check-fences-availability-for-tiling-oper.patch \
		   file://0003-media-driver-RPL-S-enablement.patch \
		   file://0002-media-driver-RPL-P-enablement.patch \
                 "

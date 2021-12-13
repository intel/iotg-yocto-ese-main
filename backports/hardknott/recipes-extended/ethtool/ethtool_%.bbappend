FILESEXTRAPATHS:prepend := "${THISDIR}/ethtool:"

SRC_URI:append = " \
		  file://0001-ethtool-Add-support-for-configuring-frame-preemption.patch \
 		  file://0002-igc-Add-support-for-dumping-frame-preemption-stats-r.patch \
 		  file://0003-ethtool-Fix-usage-options-naming.patch \
		 "

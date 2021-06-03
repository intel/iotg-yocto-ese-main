FILESEXTRAPATHS_prepend := "${THISDIR}/ethtool:"

SRC_URI_append = " \
		  file://0001-ethtool-Add-support-for-configuring-frame-preemption.patch \
 		  file://0002-ethtool-Add-support-for-configuring-frame-preemption.patch \
 		  file://0003-igc-Add-support-for-dumping-frame-preemption-stats-r.patch \
 		  file://0004-ethtool-Fix-usage-options-naming.patch \
		 "

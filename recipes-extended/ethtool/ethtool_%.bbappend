FILESEXTRAPATHS:prepend := "${THISDIR}/ethtool:"

SRC_URI:append = " \
		  file://0001-include-uapi-Update-the-local-headers-with-Frame-Pre.patch \
 		  file://0002-ethtool-Add-support-for-configuring-frame-preemption.patch \
 		  file://0003-igc-Add-support-for-dumping-frame-preemption-stats-r.patch \
                  file://0004-preempt-Add-support-for-verifying-frame-preemption.patch \
		  file://0005-ethtool-Fix-the-enum-message-type.patch \
		 "

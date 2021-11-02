SRC_URI:append = "file://0001-Add-IGC-driver-support.patch \
                  file://0001-ethtool-Add-support-for-configuring-frame-preemption.patch \
                  file://0001-igc-Add-support-for-dumping-frame-preemption-stats-r.patch \
		  file://0001-ethtool-Fix-usage-options-naming.patch \
"


FILESEXTRAPATHS:prepend := "${THISDIR}/ethtool:"

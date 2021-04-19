SRC_URI_append = "file://0001-uapi-linux-update-kernel-UAPI-header-files.patch \
                  file://0002-ioctl-check-presence-of-eeprom-length-argument-prope.patch \
                  file://0003-settings-simplify-link_mode_info-initializers.patch \
		  file://0004-ioctl-convert-cmdline_info-arrays-to-named-initializ.patch \
                  file://0005-update-UAPI-header-copies.patch \
		  file://0006-tunnels-implement-new-show-tunnels-command.patch \
                  file://0007-update-link-mode-tables.patch \
		  file://0008-update-UAPI-header-copies.patch \
                  file://0009-netlink-add-tunnel-offload-format-descriptions.patch \
		  file://0010-ethtool-Add-support-for-configuring-frame-preemption.patch \
                  file://0011-ethtool-Add-support-for-configuring-frame-preemption.patch \
		  file://0012-igc-Add-support-for-dumping-frame-preemption-stats-r.patch \
		  file://0013-ethtool-Fix-usage-options-naming.patch \
"


FILESEXTRAPATHS_prepend := "${THISDIR}/ethtool:"

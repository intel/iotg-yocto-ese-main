platform_type="$(/usr/sbin/dmidecode -s baseboard-product-name)"

#default driver is iris for any platforms other than CometLake
case "$platform_type" in
	*"CometLake"* )
		;;
	*)
		if [ -f "/sys/kernel/debug/dri/1/i915_sriov_info" ]; then
			is_vf=`cat /sys/kernel/debug/dri/1/i915_sriov_info | grep sriov | cut -d " " -f2`
		elif [ -f "/sys/kernel/debug/dri/0/i915_sriov_info" ]; then
			is_vf=`cat /sys/kernel/debug/dri/0/i915_sriov_info | grep sriov | cut -d " " -f2`
		fi

		if [[ $is_vf == "VF" ]]; then
			export MESA_LOADER_DRIVER_OVERRIDE=pl111
		else
			export MESA_LOADER_DRIVER_OVERRIDE=iris
		fi
		;;
esac

platform_type="$(/usr/sbin/dmidecode -s baseboard-product-name)"

#default driver is iris for any platforms other than CometLake
case "$platform_type" in
	*"CometLake"* )
		;;
	*)
		export MESA_LOADER_OVERRIDE_DRIVER=iris
		;;
esac

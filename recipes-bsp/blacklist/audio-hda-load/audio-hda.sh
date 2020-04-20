#!/bin/sh
platform_type=$(/usr/sbin/dmidecode -s baseboard-product-name)

case "$platform_type" in
	*"ElkhartLake"* )
		modprobe -b snd-sof-pci
		;;
	*)
		modprobe -b snd-hda-intel
		;;
esac

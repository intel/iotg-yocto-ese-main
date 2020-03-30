#!/bin/sh

platform_type=$(/usr/sbin/dmidecode -s baseboard-product-name)

case "$platform_type" in
	*"ElkhartLake"* )
		modprobe -b snd_hda_codec_realtek
		modprobe -b snd_soc_rt5660
		;;
	*)
		modprobe -b snd-hda-intel
		;;
esac

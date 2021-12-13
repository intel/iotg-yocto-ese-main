# usage IMGCLASSES:append = " image-live-injector"
# primarily for injecting slimboot container files into hddimg images

populate_kernel:append(){
	# allows for forced kernel
	if [ -n "${IMAGE_LIVE_INJECTOR_KERNEL}" ]; then
		cp -L "${IMAGE_LIVE_INJECTOR_KERNEL}" $dest/${KERNEL_IMAGETYPE}
	fi
	# allows for sbl_os slim boot container
	if [ -n "${IMAGE_LIVE_INJECTOR_SBL}" ]; then
		cp -L "${IMAGE_LIVE_INJECTOR_SBL}" $dest/sbl_os
	fi
	if [ -n "${IMAGE_LIVE_INJECTOR_OTHERS}" ]; then
		for i in ${IMAGE_LIVE_INJECTOR_OTHERS}; do
			cp -L ${i} $dest/
		done
	fi
}

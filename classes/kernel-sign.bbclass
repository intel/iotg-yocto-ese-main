kernel_do_install_append() {
	sb-keymgmt.py -c sign -kn ${DEPLOY_DIR_IMAGE}/yocto.key -cn ${DEPLOY_DIR_IMAGE}/yocto.crt -usf ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} -sf ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_PACKAGE_NAME}
}

do_install[depends] += "shim:do_deploy"
DEPENDS += "sb-keymgmt-native"

python(){
    d.appendVar('PACKAGES', ' ' + d.getVar('KERNEL_PACKAGE_NAME') + '-image-signed')
    d.setVar(d.expand("FILES_${KERNEL_PACKAGE_NAME}-image-signed"), d.expand("/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_PACKAGE_NAME}"))
}

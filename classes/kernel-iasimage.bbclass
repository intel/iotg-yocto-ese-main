inherit python3native
fakeroot do_iasimage() {
	echo "${APPEND}" > ${WORKDIR}/ias_cmdline.txt
	${PYTHON} ${STAGING_BINDIR_NATIVE}/iasimage create -o ${D}/${KERNEL_IMAGEDEST}/${IASIMAGE_NAME} -i 0x30300 -d ${IAS_KEY_PATH} ${WORKDIR}/ias_cmdline.txt ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} ${IASIMAGE_INITRD_PATH}
}

kernel_do_deploy_append() {
	install -m 0644 ${D}/${KERNEL_IMAGEDEST}/${IASIMAGE_NAME} ${DEPLOYDIR}/${IASIMAGE_NAME}
}

do_iasimage[doc] = "Packs kernel commandline, image and initrd for slimboot OSLoader payload"
addtask iasimage after do_install before kernel_do_deploy

DEPENDS += "iasimage-native ${PYTHON_PN}-cryptography-native ${PYTHON_PN}-idna-native"
do_iasimage[depends] += "${PN}:do_install ${IASIMAGE_DEPENDS}"
do_package[depends] += "${PN}:do_iasimage"

IASIMAGE_INITRD_PATH ?= ""
IASIMAGE_DEPENDS ?= ""
BASE_IASIMAGE = "ias.bin"
IASIMAGE_NAME = "${BASE_IASIMAGE}"

python(){
    d.appendVar('PACKAGES', d.expand(' ${KERNEL_PACKAGE_NAME}-image-iasimage'))

    # fix host-user-contaminated QA warnings
    d.setVarFlag('do_iasimage', 'fakeroot', '1')
    d.setVarFlag('do_iasimage', 'umask', '022')

    name = d.getVar('KERNEL_PACKAGE_NAME')
    if name == 'kernel':
        d.setVar('IASIMAGE_NAME', d.expand("${BASE_IASIMAGE}"))
    else:
       d.setVar('IASIMAGE_NAME', d.expand('${BASE_IASIMAGE}-${KERNEL_PACKAGE_NAME}'))

    d.setVar(d.expand("FILES_${KERNEL_PACKAGE_NAME}-image-iasimage"), d.expand("/${KERNEL_IMAGEDEST}/${IASIMAGE_NAME}"))
}

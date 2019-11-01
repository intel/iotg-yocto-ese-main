# slimboot image, based on iasimage
# unable to currently handle initrd SBLIMAGE_INITRD_PATH
inherit python3native
fakeroot do_sblimage() {
	mkdir -p ${WORKDIR}/slimboot
	echo "${APPEND}" > ${WORKDIR}/slimboot/sbl_cmdline.txt
	${PYTHON} ${STAGING_DIR_NATIVE}/${libexecdir}/slimboot/Tools/GenContainer.py create -cl CMDL:${WORKDIR}/slimboot/sbl_cmdline.txt \
		KRNL:${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} INRD:${SBLIMAGE_INITRD_PATH} \
		-o ${WORKDIR}/slimboot/${SBLIMAGE_NAME} \
		-k ${IAS_KEY_PATH} -t CLASSIC
	install -m 644 ${WORKDIR}/slimboot/${SBLIMAGE_NAME} ${D}/${KERNEL_IMAGEDEST}/${SBLIMAGE_NAME}
}

kernel_do_deploy_append() {
	install -m 0644 ${D}/${KERNEL_IMAGEDEST}/${SBLIMAGE_NAME} ${DEPLOYDIR}/${SBLIMAGE_NAME}
}

do_sblimage[doc] = "Packs kernel commandline, image and initrd for slimboot OSLoader payload (GenContainer)"
addtask sblimage after do_install before kernel_do_deploy

DEPENDS += "slimboot-tools-native ${PYTHON_PN}-cryptography-native ${PYTHON_PN}-idna-native"
do_sblimage[depends] += "${PN}:do_install ${SBLIMAGE_DEPENDS}"
do_package[depends] += "${PN}:do_sblimage"

SBLIMAGE_INITRD_PATH ?= ""
SBLIMAGE_DEPENDS ?= ""
BASE_SBLIMAGE = "sbl_os"
SBLIMAGE_NAME = "${BASE_SBLIMAGE}"

python(){
    d.appendVar('PACKAGES', d.expand(' ${KERNEL_PACKAGE_NAME}-image-sblimage'))

    # fix host-user-contaminated QA warnings
    d.setVarFlag('do_sblimage', 'fakeroot', '1')
    d.setVarFlag('do_sblimage', 'umask', '022')

    name = d.getVar('KERNEL_PACKAGE_NAME')
    if name == 'kernel':
        d.setVar('SBLIMAGE_NAME', d.expand("${BASE_SBLIMAGE}"))
    else:
        d.setVar('SBLIMAGE_NAME', d.expand('${BASE_SBLIMAGE}-${KERNEL_PACKAGE_NAME}'))

    d.setVar(d.expand("FILES_${KERNEL_PACKAGE_NAME}-image-sblimage"), d.expand("/${KERNEL_IMAGEDEST}/${SBLIMAGE_NAME}"))
}

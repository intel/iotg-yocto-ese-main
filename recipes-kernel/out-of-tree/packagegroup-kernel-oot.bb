SUMMARY = "Out of tree kernel drivers convenience installer"
# see classes/kernel-oot*.bbclass

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

RDEPENDS_${PN} = ""
ALLOW_EMPTY_${PN} = "1"

python(){
    pn = d.getVar('PN')
    modlist = d.getVar('KERNEL_PROVIDERS_EXTRA_MODULES') or ''
    kernels = d.getVar('KERNEL_PROVIDERS') or ''

    for k in kernels.strip().split():
        for m in modlist.strip().split():
            d.appendVar('RDEPENDS_' + pn, ' %s-%s' % (k, m))
}


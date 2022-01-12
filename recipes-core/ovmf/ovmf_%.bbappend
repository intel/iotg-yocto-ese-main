do_install:append:class-target() {
    # For qemu
    install -d ${D}/${datadir}/${PN}-edk2
    install ${WORKDIR}/ovmf/Shell.efi ${D}/${datadir}/${PN}-edk2
    install ${WORKDIR}/ovmf/EnrollDefaultKeys.efi ${D}/${datadir}/${PN}-edk2
    if ${@bb.utils.contains('PACKAGECONFIG', 'secureboot', 'true', 'false', d)}; then
        install ${WORKDIR}/ovmf/EnrollDefaultKeys.efi ${D}/${datadir}/${PN}
    fi
    for i in \
        ovmf \
        ovmf.code \
        ovmf.vars \
        ${@bb.utils.contains('PACKAGECONFIG', 'secureboot', 'ovmf.secboot ovmf.secboot.code', '', d)} \
        ; do
        install ${WORKDIR}/ovmf/$i.fd ${D}/${datadir}/${PN}-edk2/$i.fd
    done
}

PACKAGES:append:class-target = " ${PN}-edk2"
FILES:${PN}-edk2:class-target = "${datadir}/${PN}-edk2"

# WA for fetch failure of cmocka . Added a mirror    
MIRRORS:append = " gitsm://git.cryptomilk.org/projects/cmocka.git gitsm://github.com/tianocore/edk2-cmocka.git"

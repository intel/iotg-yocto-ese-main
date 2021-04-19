do_install_append_class-target() {
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

do_fix_line_ending() {
	sed -e 's@\r@@g' -i ${S}/BaseTools/Source/C/Makefiles/header.makefile
	sed -e 's@\r@@g' -i ${S}/BaseTools/Conf/tools_def.template
	sed -e 's@\r@@g' -i ${S}/OvmfPkg/Bhyve/BhyveX64.dsc
	sed -e 's@\r@@g' -i ${S}/OvmfPkg/OvmfPkgIa32.dsc
	sed -e 's@\r@@g' -i ${S}/OvmfPkg/OvmfPkgIa32X64.dsc
	sed -e 's@\r@@g' -i ${S}/OvmfPkg/OvmfPkgX64.dsc
	sed -e 's@\r@@g' -i ${S}/OvmfPkg/OvmfXen.dsc
}

addtask fix_line_ending after do_unpack before do_patch

PACKAGES_append_class-target = " ${PN}-edk2"
FILES_${PN}-edk2_class-target = "${datadir}/${PN}-edk2"

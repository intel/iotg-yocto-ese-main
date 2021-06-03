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

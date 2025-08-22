FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "git://github.com/intel/intel-graphics-compiler.git;protocol=https;name=igc;branch=releases/2.10.x \
           git://github.com/intel/vc-intrinsics.git;protocol=https;destsuffix=git/vc-intrinsics;name=vc;nobranch=1 \
           git://github.com/KhronosGroup/SPIRV-Tools.git;protocol=https;destsuffix=git/SPIRV-Tools;name=spirv-tools;branch=main \
           git://github.com/KhronosGroup/SPIRV-Headers.git;protocol=https;destsuffix=git/SPIRV-Headers;name=spirv-headers;branch=main \
           file://0003-Improve-Reproducibility-for-src-package.patch \
           file://0001-BiF-CMakeLists.txt-remove-opt-from-DEPENDS.patch \
           file://0001-external-SPIRV-Tools-change-path-to-tools-and-header.patch \
           file://0001-Build-not-able-to-locate-BiFManager-bin.patch \
           "

S = "${WORKDIR}/git"          

EXTRA_OECMAKE += " -DLLVMGenXIntrinsics_DIR=${STAGING_LIBDIR}"


#fix lib64 for vc-intrinsics
do_fix_lib64() {
    libsubstr="lib64"
    #check if the libdir is 64 or not
    if [ -z "${libdir##*$libsubstr*}" ]; then
        #check if the lib directory existence
        if [ ! -d "${STAGING_DIR_HOST}${prefix}/lib" ]; then
            mkdir -p ${STAGING_DIR_HOST}${prefix}/lib
        fi
        ln -sf ${STAGING_LIBDIR}/libLLVMGenXIntrinsics.a ${STAGING_DIR_HOST}${prefix}/lib/
    fi
}

addtask fix_lib64 after do_populate_lic before do_configure

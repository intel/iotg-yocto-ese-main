FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
EXTRA_OECMAKE += " -DLLVMGenXIntrinsics_DIR=${STAGING_LIBDIR}"

SRC_URI:append = " file://0001-Add-ADL-N-enum-value.patch \
                   file://0002-Added-some-Alderlake-related-functionality.patch \
                 "
                 
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

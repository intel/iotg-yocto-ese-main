SUMMARY = "The Intel(R) Graphics Compiler for OpenCL(TM)"
DESCRIPTION = "The Intel(R) Graphics Compiler for OpenCL(TM) is an \
llvm based compiler for OpenCL(TM) targeting Intel Gen graphics \
hardware architecture."

LICENSE = "MIT & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://IGC/BiFModule/Implementation/ExternalLibraries/libclc/LICENSE.TXT;md5=311cfc1a5b54bab8ed34a0b5fba4373e \
                    file://IGC/Compiler/LegalizationPass.cpp;beginline=1;endline=25;md5=4abf1738ff96b18e34186eb763e28eeb \
                    file://NOTICES.txt;md5=b12e73994de4fbe0f688cf0bc91512a0"

SRC_URI = "git://github.com/intel/intel-graphics-compiler.git;protocol=https \
           file://0001-skip-execution-of-ElfPackager.patch \
           file://link-to-LLVMGenXIntrinsics.patch \
           "

SRCREV = "3623209b10b357ddb3a3d6eac3551c53ebc897f7"
PV = "git+${SRCPV}"

S = "${WORKDIR}/git"

inherit cmake

COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST_libc-musl = "null"

DEPENDS += " flex-native bison-native clang opencl-clang vc-intrinsics"
DEPENDS_append_class-target = " clang-cross-x86_64"

RDEPENDS_${PN} += "opencl-clang"

EXTRA_OECMAKE = "-DIGC_PREFERRED_LLVM_VERSION=9.0.0 -DPYTHON_EXECUTABLE=${HOSTTOOLS_DIR}/python3 -DLLVMGenXIntrinsics_DIR=${STAGING_LIBDIR} -DINSTALL_SPIRVDLL=0"

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

BBCLASSEXTEND = "native nativesdk"

UPSTREAM_CHECK_GITTAGREGEX = "^igc-(?P<pver>(?!19\..*)\d+(\.\d+)+)$"

FILES_${PN} += " \
                ${libdir}/igc/NOTICES.txt \
                "

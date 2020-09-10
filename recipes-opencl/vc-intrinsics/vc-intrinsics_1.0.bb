SUMMARY = "VC Intrinsics"
DESCRIPTION = "VC Intrinsics project contains a set of new intrinsics on \
top of core LLVM IR instructions that represent SIMD semantics of a program \
targeting GPU"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://License.md;md5=c18ea6bb4786a26bf4eee88a7424a408"

SRC_URI = "git://github.com/intel/vc-intrinsics.git;protocol=https; \
          "

SRCREV = "f39ff1e6589f8bda1bcd81a9260f8143996b8a19"

S = "${WORKDIR}/git"

inherit cmake

COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST_libc-musl = "null"

DEPENDS += "  clang"

EXTRA_OECMAKE = "-DLLVM_DIR=${STAGING_LIBDIR} -DPYTHON_EXECUTABLE=${HOSTTOOLS_DIR}/python3"

do_install_append() {
    install -d ${D}${libdir}/cmake/LLVMGenXIntrinsics
    install -m 644 ${B}/GenXIntrinsics/lib/GenXIntrinsics/*.a ${D}${libdir}
    cp -r ${B}/GenXIntrinsics/CMakeFiles/Export/lib/cmake/LLVMGenXIntrinsics/*.cmake ${D}${libdir}/cmake/LLVMGenXIntrinsics
}

FILES_${PN} += "${prefix}/lib/cmake/LLVMGenXIntrinsics/*.cmake"
FILES_${PN}-staticdev += "${prefix}/lib/*.a"

BBCLASSEXTEND = "native nativesdk"
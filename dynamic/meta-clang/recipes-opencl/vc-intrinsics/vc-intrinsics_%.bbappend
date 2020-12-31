SUMMARY = "VC Intrinsics"
DESCRIPTION = "VC Intrinsics project contains a set of new intrinsics on \
top of core LLVM IR instructions that represent SIMD semantics of a program \
targeting GPU"


EXTRA_OECMAKE += " -DPYTHON_EXECUTABLE=${HOSTTOOLS_DIR}/python3"

do_install_append() {
    install -d ${D}${libdir}/cmake/LLVMGenXIntrinsics
    install -m 644 ${B}/GenXIntrinsics/lib/GenXIntrinsics/*.a ${D}${libdir}
    cp -r ${B}/GenXIntrinsics/CMakeFiles/Export/lib/cmake/LLVMGenXIntrinsics/*.cmake ${D}${libdir}/cmake/LLVMGenXIntrinsics
}

FILES_${PN} += "${prefix}/lib/cmake/LLVMGenXIntrinsics/*.cmake"
FILES_${PN}-staticdev += "${prefix}/lib/*.a"

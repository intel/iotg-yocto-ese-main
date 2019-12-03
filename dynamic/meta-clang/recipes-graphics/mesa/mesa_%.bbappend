LDFLAGS_append_toolchain-clang = " -latomic -lm"
DEPENDS_append_toolchain-clang = " libatomic-ops"

EXTRA_OEMASON_append_toolchain-clang_x86 = " -Dasm=false"
EXTRA_OEMASON_append_toolchain-clang_x86-64 = " -Dasm=false"

export YOCTO_ALTERNATE_EXE_PATH = "${STAGING_LIBDIR}/llvm-config"
export LLVM_CONFIG = "${STAGING_BINDIR_NATIVE}/llvm-config"

PACKAGECONFIG[gallium-llvm] = "-Dllvm=true -Dshared-llvm=true, -Dllvm=false, clang clang-native \
${@'elfutils' if ${GALLIUMDRIVERS_LLVM33_ENABLED} else ''}"

inherit qemu
DEPENDS_append = " qemu-native"
SYSROOT_DIRS_append = " ${bindir}"

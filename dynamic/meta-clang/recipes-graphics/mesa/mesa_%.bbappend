LDFLAGS_append_toolchain-clang = " -latomic -lm"
DEPENDS_append_toolchain-clang = " libatomic-ops"

EXTRA_OEMASON_append_toolchain-clang_x86 = " -Dasm=false"
EXTRA_OEMASON_append_toolchain-clang_x86-64 = " -Dasm=false"

export YOCTO_ALTERNATE_EXE_PATH = "${STAGING_LIBDIR}/llvm-config"
export LLVM_CONFIG = "${STAGING_BINDIR_NATIVE}/llvm-config"

inherit qemu
DEPENDS_append = " qemu-native"
SYSROOT_DIRS_append = " ${bindir}"

do_configure_prepend(){
       sed -e 's@llvm-config${LLVMVERSION}@${LLVM_CONFIG}@g' -i "${WORKDIR}/meson.cross"
}

# fix multilib
do_prepare_recipe_sysroot[postfuncs] += "do_llvm_fixup"
do_llvm_fixup(){
       if [ "${base_libdir}" != "lib" ]; then
               for i in ${STAGING_LIBDIR}/libLLVM*; do
                       ln -sf ${i} ${STAGING_DIR_HOST}/${prefix}/lib/
               done
       fi
}

# use meta-clang
# replace llvm-native with clang-native, llvm${MESA_LLVM_RELEASE} with clang
PACKAGECONFIG[gallium-llvm] = "-Dllvm=true -Dshared-llvm=true, -Dllvm=false, clang clang-native \
                                ${@'elfutils' if ${GALLIUMDRIVERS_LLVM33_ENABLED} else ''}"

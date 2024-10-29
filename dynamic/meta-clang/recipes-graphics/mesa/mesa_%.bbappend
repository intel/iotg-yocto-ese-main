export YOCTO_ALTERNATE_EXE_PATH = "${STAGING_LIBDIR}/llvm-config"
export LLVM_CONFIG = "${STAGING_BINDIR_NATIVE}/llvm-config"

inherit qemu
DEPENDS:append = " qemu-native"
SYSROOT_DIRS:append = " ${bindir}"

do_configure:prepend(){
       sed -e 's@llvm-config${LLVMVERSION}@${LLVM_CONFIG}@g' -i "${WORKDIR}/meson.cross"
}

# fix multilib
do_prepare_recipe_sysroot[postfuncs] += "do_llvm_fixup"
do_llvm_fixup(){
       if [ "${baselib}" != "lib" ]; then
               for i in ${STAGING_LIBDIR}/libLLVM*; do
                       ln -sf ${i} ${STAGING_DIR_HOST}/${prefix}/lib/
               done
       fi
}

# use meta-clang
# replace llvm-native with clang-native, llvm${MESA_LLVM_RELEASE} with clang

CLANG_GALLIUM_LLVM = "-Dllvm=true -Dshared-llvm=true -Ddraw-use-llvm=true,-Dllvm=false,clang clang-native elfutils"

PACKAGECONFIG[gallium-llvm] := "${@[d.getVarFlag('PACKAGECONFIG', 'gallium-llvm'), '${CLANG_GALLIUM_LLVM}'][d.getVar('TOOLCHAIN') == 'clang']}"

# PACKAGECONFIG "opencl" requires libclc from meta-clang
PACKAGECONFIG:append = " opencl"

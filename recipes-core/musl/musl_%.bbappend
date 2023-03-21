# Fix glib-2.0 configure issue "qemu-x86_64: Could not open '/lib64/ld-musl-x86_64.so.1': No such file or directory"
# due to openembedded-core, kirkstone, commit d821a602c56a8d0c8171ee0d2ce31613121be3a6
# Macro SYSTEMLIBS_DIR used in GCC source code depends on baselib and baselib is equal to "lib64".
do_install:append() {
       if [ "${baselib}" = "lib64" ]; then
                install -m 0755 -d ${D}/${baselib}
                ln -rs ${D}${libdir}/libc.so ${D}/${baselib}/ld-musl-${MUSL_LDSO_ARCH}.so.1
        fi
}

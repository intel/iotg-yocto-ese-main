# fix runtime linker, must always be in /lib
# https://www.openwall.com/lists/musl/2015/04/22/4

python() {
    import os.path

    hint = os.path.basename(get_musl_loader(d))
    d.setVar("MUSL_LOADER_BASENAME", hint)
}

do_install_append(){
	if [ "${base_libdir}" != "${nonarch_base_libdir}" -a ! -f "${D}/${nonarch_base_libdir}/${MUSL_LOADER_BASENAME}" ]; then
		mkdir -p "${D}/${nonarch_base_libdir}"
		lnr "${D}/${base_libdir}/${MUSL_LOADER_BASENAME}" "${D}/${nonarch_base_libdir}/${MUSL_LOADER_BASENAME}"
	fi
}

FILES_${PN} += "${nonarch_base_libdir}"
INSANE_SKIP_${PN} += "libdir"

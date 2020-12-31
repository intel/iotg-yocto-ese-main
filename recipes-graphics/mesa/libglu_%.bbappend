FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

python() {
    import re

    version = d.getVar('PV')
    if re.match('^9.0.0', version):
        d.appendVar('SRC_URI', ' file://0001-src-libutil-error.c-fix-undeclared-indentifier-NULL.patch')
}


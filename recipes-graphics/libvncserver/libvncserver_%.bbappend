FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

python() {
    import re

    version = d.getVar('PV')
    if re.match('^0\.9\.12\+gitAUTOINC\+c0a23857a5', version):
        d.appendVar('SRC_URI', ' file://0001-CMake-replace-hardcoded-lib-with-CMAKE_INSTALL_LIBDI.patch')
        d.appendVar('SRC_URI', ' file://0002-CMake-replace-lib-with-CMAKE_INSTALL_LIBDIR-for-pkgc.patch')
}


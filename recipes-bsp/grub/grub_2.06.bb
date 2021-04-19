require ./grub_${PV}.inc

inherit bash-completion
RDEPENDS_${PN}-common += "${PN}-editenv"
RDEPENDS_${PN} += "diffutils freetype ${PN}-common"

RPROVIDES_${PN}-editenv += "${PN}-efi-editenv"

#### transitional
PROVIDES_append_class-native = " grub-efi-native"
RPROVIDES_${PN}-common_append_class-native = " grub-efi-native"
RREPLACES_${PN}-common_append_class-native = " grub-efi-native"
RCONFLICTS_${PN}-common_append_class-native = " grub-efi-native"
# fix meta-mender conflict
DEPENDS_remove = "grub-efi-native"
####

PACKAGES =+ "${PN}-editenv ${PN}-common ${PN}-systemd"
FILES_${PN}-editenv = "${bindir}/grub-editenv"
FILES_${PN}-common = " \
    ${bindir} \
    ${sysconfdir} \
    ${sbindir} \
    ${datadir}/grub \
"

do_install_append () {
    install -d ${D}${sysconfdir}/grub.d
}

INSANE_SKIP_${PN} = "arch"
INSANE_SKIP_${PN}-dbg = "arch"
BBCLASSEXTEND = "native"

TESTtest[string___bo] = "test*test"

inherit systemd
SYSTEMD_PACKAGES = "${PN}-systemd"
FILES_${PN}-systemd = "${systemd_unitdir}"
do_install_append(){
  if test -n "${systemd_unitdir}" -a "${systemd_unitdir}" != "${prefix}/lib/systemd/system" -a -d "${D}${prefix}/lib/systemd/system" ; then
    install -m 755 -d "${D}${systemd_unitdir}"
    mv -T "${D}${prefix}/lib/systemd/system" "${D}${systemd_unitdir}"
    rm -rf ${D}${prefix}/lib/systemd
  fi
}

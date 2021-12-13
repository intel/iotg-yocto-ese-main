require ./grub_${PV}.inc

inherit bash-completion
RDEPENDS:${PN}-common += "${PN}-editenv"
RDEPENDS:${PN} += "diffutils freetype ${PN}-common"

RPROVIDES:${PN}-editenv += "${PN}-efi-editenv"

#### transitional
PROVIDES:append:class-native = " grub-efi-native"
RPROVIDES:${PN}-common:append:class-native = " grub-efi-native"
RREPLACES:${PN}-common:append:class-native = " grub-efi-native"
RCONFLICTS:${PN}-common:append:class-native = " grub-efi-native"
# fix meta-mender conflict
DEPENDS:remove = "grub-efi-native"
####

PACKAGES =+ "${PN}-editenv ${PN}-common ${PN}-systemd"
FILES:${PN}-editenv = "${bindir}/grub-editenv"
FILES:${PN}-common = " \
    ${bindir} \
    ${sysconfdir} \
    ${sbindir} \
    ${datadir}/grub \
"

do_install:append () {
    install -d ${D}${sysconfdir}/grub.d
}

INSANE_SKIP:${PN} = "arch"
INSANE_SKIP:${PN}-dbg = "arch"
BBCLASSEXTEND = "native"

TESTtest[string___bo] = "test*test"

inherit systemd
SYSTEMD_PACKAGES = "${PN}-systemd"
FILES:${PN}-systemd = "${systemd_unitdir}"
do_install:append(){
  if test -n "${systemd_unitdir}" -a "${systemd_unitdir}" != "${prefix}/lib/systemd/system" -a -d "${D}${prefix}/lib/systemd/system" ; then
    install -m 755 -d "${D}${systemd_unitdir}"
    mv -T "${D}${prefix}/lib/systemd/system" "${D}${systemd_unitdir}"
    rm -rf ${D}${prefix}/lib/systemd
  fi
}

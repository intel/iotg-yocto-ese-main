require grub204.inc

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

PACKAGES =+ "${PN}-editenv ${PN}-common"
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

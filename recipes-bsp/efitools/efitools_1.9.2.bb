HOMEPAGE = "https://git.kernel.org/pub/scm/linux/kernel/git/jejb/efitools.git/"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/jejb/efitools.git;protocol=https;nobranch=1 \
           file://yocto-tweaks.patch \
           file://0002-lib-console.c-fix-typo.patch \
           file://fix-warnings.patch \
"
SRCREV = "392836a46ce3c92b55dc88a1aebbcfdfc5dcddce"
LICENSE = "GPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=e28f66b16cb46be47b20a4cdfe6e99a1"

CERT_DEP = ""
CERT_DEP_DEPLOY = ""
CERT_DEP:class-target = "${@bb.utils.contains('IMAGE_FEATURES', 'efi-lockdown', 'virtual/secure-boot-certificates', '', d)}"
CERT_DEP_DEPLOY:class-target = "${@bb.utils.contains('IMAGE_FEATURES', 'efi-lockdown', 'virtual/secure-boot-certificates:do_deploy', '', d)}"
# workaround for caching old keys causing key mismatch
DEPENDS:append:class-target += "${CERT_DEP}"
do_compile[depends] += "${CERT_DEP_DEPLOY}"
do_compile[recideptask] += "${CERT_DEP_DEPLOY}"
NEED_LOCKDOWN = "${@bb.utils.contains('IMAGE_FEATURES', 'efi-lockdown', '1', '', d)}"
inherit ${@bb.utils.contains('IMAGE_FEATURES', 'efi-lockdown', 'gnu-efi', '', d)}

S = "${WORKDIR}/git"
DEPENDS = "openssl gnu-efi libfile-slurp-perl-native help2man-native openssl-native efitools-native"
inherit gnu-efi perlnative
TUNE_CCARGS:remove = "-mfpmath=sse"

EXTRA_OEMAKE += "CRTPATH=${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH} \
  ARCH=${GNU_EFI_ARCH} LDSCRIPT=efi.lds EFI_INC=${STAGING_INCDIR} \
  CRTOBJ=crt0.o PREFIX=${prefix} CROSS_PREFIX=${TARGET_PREFIX} \
"

EXTRA_OEMAKE:append:class-native = " CROSS_BIN=./"
EXTRA_OEMAKE:append:class-target = " CROSS_BIN="
EXTRA_OEMAKE:append:x86-64 = " FORMAT=--target=efi-app-x86_64"

do_compile:prepend:class-target(){
	if test -n "${NEED_LOCKDOWN}"; then
		cp "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.crt" DB.crt
		cp "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.key" DB.key
		cp "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/KEK.crt" KEK.crt
		cp "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/KEK.key" KEK.key
		cp "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/PK.crt" PK.crt
		cp "${DEPLOY_DIR_IMAGE}/secure-boot-certificates/PK.key" PK.key
		oe_runmake LockDown.efi
	fi
}
do_compile(){
	oe_runmake all
}

do_install(){
	oe_runmake install DESTDIR=${D}
}

BBCLASSEXTEND = "native"

inherit deploy
do_deploy(){
	:
}
do_deploy:append:class-target(){
	if test -n "${NEED_LOCKDOWN}"; then
		install -m 755 -d ${DEPLOYDIR}/${BPN}
		install -m 644 LockDown.efi ${DEPLOYDIR}/${BPN}/LockDown${GNU_EFI_ARCH}.efi
		install -m 644 KeyTool.efi ${DEPLOYDIR}/${BPN}/KeyTool${GNU_EFI_ARCH}.efi
	fi
}
addtask deploy after do_compile before do_build

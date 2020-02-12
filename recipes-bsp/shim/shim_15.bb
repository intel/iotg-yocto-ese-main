SUMMARY = "shim is a trivial EFI application that, when run, attempts to open and \
execute another application."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=b92e63892681ee4e8d27e7a7e87ef2bc"

SRC_URI = "git://github.com/rhboot/shim.git;protocol=https \
	file://170.patch \
"
S = "${WORKDIR}/git"
SRCREV = "51413d1deb0df0debdf1d208723131ff0e36d3a3"

inherit deploy

DEPENDS += " gnu-efi nss grub-efi sb-keymgmt-native sbsigntool-native"

ALLOW_EMPTY_${PN} = "1"

TUNE_CCARGS_remove = "-mfpmath=sse"

export INCDIR = "${STAGING_INCDIR}"
export LIBDIR = "${STAGING_LIBDIR}"
export ARCH = "${TARGET_ARCH}"

COMPATIBLE_HOST = '(x86_64.*|i686)-linux'

do_configure_append() {
	if [ -e "${MOK_KEY_PATH}" -a -e "${MOK_CERT_PATH}" ] ; then
		cp "${MOK_KEY_PATH}" yocto.key
		cp "${MOK_CERT_PATH}" yocto.crt
		touch -m yocto.key yocto.crt
	else
		sb-keymgmt.py -c gen -kn yocto.key -kl 2048 -cn yocto.crt -sn "/CN=Yocto BSP Signing Key/" -vd 365
	fi
	sb-keymgmt.py -c to_cer -cn yocto.crt -cer yocto.cer
}

do_compile_prepend() {
        unset CFLAGS LDFLAGS
}

do_compile() {
	if [ -e "${SHIM_KEY_PATH}" -a -e "${SHIM_CERT_PATH}" ] ; then
		cp "${SHIM_KEY_PATH}" shim.key
		cp "${SHIM_CERT_PATH}" shim.crt
		touch -m shim.key shim.crt
	fi
        oe_runmake VENDOR_CERT_FILE=yocto.cer CROSS_COMPILE=${TARGET_PREFIX} \
	EFI_INCLUDE="${STAGING_INCDIR}/efi" EFI_PATH="${STAGING_LIBDIR}" CC="${CCLD}" \
	ENABLE_SHIM_CERT=1 ENABLE_SBSIGN=1
}

addtask deploy after do_install before do_build

do_deploy_class-native() {
	:
}

do_deploy() {
	cp ${S}/mm*.efi.signed ${DEPLOYDIR}/
	cp ${S}/fb*.efi.signed ${DEPLOYDIR}/
	cp ${S}/shim*.efi ${DEPLOYDIR}/
	mv ${S}/yocto.key ${DEPLOYDIR}/
	mv ${S}/yocto.crt ${DEPLOYDIR}/
	mv ${S}/shim.key ${DEPLOYDIR}/
	mv ${S}/shim.crt ${DEPLOYDIR}/
}

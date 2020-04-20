SUMMARY = "shim is a trivial EFI application that, when run, attempts to open and \
execute another application."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=b92e63892681ee4e8d27e7a7e87ef2bc"

SRC_URI = "gitsm://github.com/rhboot/shim.git;protocol=https \
	   file://0001-Makefile-fix-absolute-path.patch \
	   file://0002-include-defaults.mk-fix-Makefile-variable.patch \
"
S = "${WORKDIR}/git"
SRCREV = "c252b9ee94c342f9074a3e9064fd254eef203a63"
PV_append = "+${SRCPV}"

inherit deploy

DEPENDS += " gnu-efi nss openssl-native dos2unix-native sb-keymgmt-native sbsigntool-native"

ALLOW_EMPTY_${PN} = "1"

TUNE_CCARGS_remove = "-mfpmath=sse"

export INCDIR = "${STAGING_INCDIR}"
export LIBDIR = "${STAGING_LIBDIR}"
export ARCH = "${TARGET_ARCH}"
DEFAULT_PREFERENCE = "-1"

# should be also arm compatible but untested
COMPATIBLE_HOST = '(x86_64.*|i686)-linux'
CLEANBROKEN = "1"
B = "${S}/build"
inherit gnu-efi

do_configure_append() {
	if [ -e "${MOK_KEY_PATH}" -a -e "${MOK_CERT_PATH}" ] ; then
		cp "${MOK_KEY_PATH}" yocto.key
		cp "${MOK_CERT_PATH}" yocto.crt
		touch -m yocto.key yocto.crt
	else
		sb-keymgmt.py -c gen -kn yocto.key -kl 2048 -cn yocto.crt -sn "/CN=Yocto BSP Signing Key/" -vd 365
	fi
	sb-keymgmt.py -c to_cer -cn yocto.crt -cer yocto.cer

	if [ -e "${SHIM_KEY_PATH}" -a -e "${SHIM_CERT_PATH}" ] ; then
		mkdir -p certdb
		cp "${SHIM_KEY_PATH}" certdb/shim.key
		cp "${SHIM_CERT_PATH}" certdb/shim.crt
		touch -m certdb/shim.key certdb/shim.crt
		sb-keymgmt.py -c to_cer -cn certdb/shim.crt -cer certdb/shim.cer
		openssl pkcs12 -export -in certdb/shim.crt -inkey certdb/shim.key -out certdb/shim.p12 -passout pass:
	fi

	cp ${STAGING_INCDIR}/efi.mk .

	# fix paths
	sed -e "s@${includedir}@${STAGING_INCDIR}@g" \
	    -e "s@${libdir}@${STAGING_LIBDIR}@g" \
	    -i efi.mk

	# fix extra comma
	echo '_EFI_CCLDLIBS = -Wl$(comma)--start-group $(foreach x,$(EFI_CCLDLIBS),$(x)) -Wl$(comma)--end-group' >> efi.mk
}

do_compile_prepend() {
        unset CFLAGS LDFLAGS
	mkdir -p "${B}"
	cd "${B}"
}

do_compile() {
	cd "${B}"

        oe_runmake VENDOR_CERT_FILE=yocto.crt CROSS_COMPILE=${TARGET_PREFIX} CC="${CCLD}" \
	EFI_INCLUDE="${STAGING_INCDIR}/efi" EFI_PATH="${STAGING_LIBDIR}" \ ENABLE_SHIM_CERT=1 ENABLE_SBSIGN=1 ENABLE_HTTPBOOT=1 ${SHIM_DEFAULT_LOADER} \
	EFI_CC="${CCLD}" EFI_HOSTCC="${CCLD}" TOPDIR="${S}/" BUILDDIR="${B}/" \
	-I "${B}" -I "${STAGING_INCDIR}" -f "${S}/Makefile"
}

addtask deploy after do_install before do_build

do_install() {
	cd "${B}"
	oe_runmake install-as-data DESTDIR=${D} TOPDIR="${S}/" BUILDDIR="${B}/" -I "${B}" -I "${STAGING_INCDIR}" -f ${S}/Makefile
}

do_deploy_class-native() {
	:
}

do_deploy() {
	cp ${B}/mm*.efi.signed ${DEPLOYDIR}/
	cp ${B}/fb*.efi.signed ${DEPLOYDIR}/
	cp ${B}/shim*.efi ${DEPLOYDIR}/
	cp ${B}/yocto.key ${DEPLOYDIR}/
	cp ${B}/yocto.crt ${DEPLOYDIR}/
        cp ${B}/certdb/shim.key ${DEPLOYDIR}/
        cp ${B}/certdb/shim.crt ${DEPLOYDIR}/
}

# string gets munged by the shell and make multiple times
python() {
  loader = d.getVar('SHIM_LOADER_IMAGE')
  if loader:
    d.setVar('SHIM_DEFAULT_LOADER', r"DEFAULT_LOADER='\\\\\\\\%s'" % loader)
  else:
    d.setVar('SHIM_DEFAULT_LOADER', '')
}

FILES_${PN} += "${datadir}"

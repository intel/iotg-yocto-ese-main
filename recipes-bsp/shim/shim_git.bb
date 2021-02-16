SUMMARY = "shim is a trivial EFI application that, when run, attempts to open and \
execute another application."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=b92e63892681ee4e8d27e7a7e87ef2bc"

SRC_URI = "gitsm://github.com/rhboot/shim.git;protocol=https;nobranch=1 \
           file://0002-lib-console.c-fix-typo.patch \
"

S = "${WORKDIR}/git"
SRCREV = "1f123ac2359cd923e9144f944a4bddf597fddbb5"
PV_append = "+${SRCPV}"

inherit deploy

DEPENDS += " gnu-efi nss openssl-native dos2unix-native sbsigntool-native elfutils-native"

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

do_configure[depends] += "virtual/secure-boot-certificates:do_deploy"
do_configure_append() {
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.crt .
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.key .
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.crt .
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.key .
	touch -m yocto.key yocto.crt shim.key shim.crt
	openssl x509 -outform DER -in yocto.crt -out yocto.cer
	openssl x509 -outform DER -in shim.crt -out shim.cer
	openssl pkcs12 -export -in shim.crt -inkey shim.key -out shim.p12 -passout pass:
}

do_compile_prepend() {
        unset CFLAGS LDFLAGS
	mkdir -p "${B}"
	cd "${B}"
}

do_compile() {
	cd "${B}"

	# native tool used during install
	${BUILD_CCLD} -Og -g3 -Wall -Werror -Wextra -o "${B}/buildid" "${S}/buildid.c" -lelf

        oe_runmake VENDOR_CERT_FILE=yocto.crt CROSS_COMPILE=${TARGET_PREFIX} CC="${CCLD}" \
	EFI_INCLUDE="${STAGING_INCDIR}/efi" EFI_PATH="${STAGING_LIBDIR}" \
        ENABLE_SHIM_CERT=1 ENABLE_SBSIGN=1 ENABLE_HTTPBOOT=1 ${SHIM_DEFAULT_LOADER} \
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
	install -m 755 -d ${DEPLOYDIR}/${PN}
	for i in mm${GNU_EFI_ARCH}.efi shim${GNU_EFI_ARCH}.efi fb${GNU_EFI_ARCH}.efi; do
		install -m644 ${B}/${i} ${DEPLOYDIR}/${PN}
	done
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

SUMMARY = "shim is a trivial EFI application that, when run, attempts to open and \
execute another application."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=b92e63892681ee4e8d27e7a7e87ef2bc"

SRC_URI = "gitsm://github.com/rhboot/shim.git;protocol=https;nobranch=1 \
           file://0002-lib-console.c-fix-typo.patch \
	   file://0001-hexdump.h-fix-arithmetic-error.patch \
"

S = "${WORKDIR}/git"
PV_append = "${SRCPV}"
SRCREV = "fc4368fed53837e00d303600d8b628cb0392b629"

inherit deploy

DEPENDS += " gnu-efi nss openssl-native dos2unix-native sbsigntool-native elfutils-native"

ALLOW_EMPTY_${PN} = "1"

TUNE_CCARGS_remove = "-mfpmath=sse"
CPPFLAGS_append = " -Wno-error=pointer-sign -Wno-error=incompatible-pointer-types -fno-strict-aliasing"

export INCDIR = "${STAGING_INCDIR}"
export LIBDIR = "${STAGING_LIBDIR}"
export ARCH = "${TARGET_ARCH}"

# should be also arm compatible but untested
COMPATIBLE_HOST = '(x86_64.*|i686)-linux'

B = "${S}"
inherit gnu-efi

do_configure[depends] += "virtual/secure-boot-certificates:do_deploy"
do_configure_append() {
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.crt \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.cer \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.key \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.crt \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.key \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.cer .
	openssl pkcs12 -export -in shim.crt -inkey shim.key -out shim.p12 -passout pass:
}


do_compile() {
	unset CFLAGS LDFLAGS

	# native tool used during install
	${BUILD_CCLD} -o "${B}/buildid" ${BUILD_CPPFLAGS} ${BUILD_LDFLAGS} "${S}/buildid.c" -lelf

        oe_runmake VENDOR_CERT_FILE=yocto.cer CROSS_COMPILE=${TARGET_PREFIX} CC="${CCLD}" \
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

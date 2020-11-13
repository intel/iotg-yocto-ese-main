SUMMARY = "shim is a trivial EFI application that, when run, attempts to open and \
execute another application."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=b92e63892681ee4e8d27e7a7e87ef2bc"

SRC_URI = "gitsm://github.com/rhboot/shim.git;protocol=https \
           file://170.patch \
           file://0001-shim-fix-Werror-pointer-sign-errors.patch \
           file://0002-lib-console.c-fix-typo.patch \
"
S = "${WORKDIR}/git"
SRCREV = "51413d1deb0df0debdf1d208723131ff0e36d3a3"

inherit deploy

DEPENDS += " gnu-efi sbsigntool-native elfutils-native"

ALLOW_EMPTY_${PN} = "1"

TUNE_CCARGS_remove = "-mfpmath=sse"

inherit gnu-efi
export INCDIR = "${STAGING_INCDIR}"
export LIBDIR = "${STAGING_LIBDIR}"
export ARCH = "${TARGET_ARCH}"

# should be also arm compatible but untested
COMPATIBLE_HOST = '(x86_64.*|i686)-linux'

do_configure[depends] += "virtual/secure-boot-certificates:do_deploy"
do_configure_append() {
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.crt .
        cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.key .
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.crt .
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.key .
	touch -m yocto.key yocto.crt shim.key shim.crt
	openssl x509 -outform DER -in yocto.crt -out yocto.cer
	openssl x509 -outform DER -in shim.crt -out shim.cer
}

EXTRA_OEMAKE += "VENDOR_CERT_FILE=yocto.cer CROSS_COMPILE=${TARGET_PREFIX} CC="${CCLD}" \
	EFI_INCLUDE="${STAGING_INCDIR}/efi" EFI_PATH="${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH}"  \
	ENABLE_SHIM_CERT=1 ENABLE_SBSIGN=1 ENABLE_HTTPBOOT=1 \
	EFI_LDS="${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH}/efi.lds" \
	${SHIM_DEFAULT_LOADER}"

do_compile_prepend() {
        unset CFLAGS LDFLAGS
	# native tool used during install
	${BUILD_CCLD} -o "${B}/buildid" ${BUILD_CPPFLAGS} ${BUILD_LDFLAGS} "${S}/buildid.c" -lelf
}

addtask deploy after do_install before do_build

do_install() {
	oe_runmake install-as-data DESTDIR=${D}
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

python() {
  loader = d.getVar('SHIM_LOADER_IMAGE')
  if loader:
    d.setVar('SHIM_DEFAULT_LOADER', r"DEFAULT_LOADER=\\\\\\\\%s" % loader)
  else:
    d.setVar('SHIM_DEFAULT_LOADER', '')
}

FILES_${PN} += "${datadir}"

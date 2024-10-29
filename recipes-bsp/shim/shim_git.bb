SUMMARY = "shim is a trivial EFI application that, when run, attempts to open and \
execute another application."
LICENSE = "BSD-2-Clause-Patent"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=b92e63892681ee4e8d27e7a7e87ef2bc"

SRC_URI = "gitsm://github.com/rhboot/shim.git;protocol=https;nobranch=1 \
           file://0001-shim-build-target-libefi.a-and-libgnuefi.a-once.patch"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
PV = "15.8+git${SRCPV}"
SRCREV = "5914984a1ffeab841f482c791426d7ca9935a5e6"

inherit deploy

DEPENDS += "nss openssl-native dos2unix-native sbsigntool-native elfutils-native"

# workaround: sometimes shim is not rebuilt even when new keys are generated
DEPENDS += "virtual/secure-boot-certificates"

ALLOW_EMPTY:${PN} = "1"

TUNE_CCARGS:remove:x86-64 = "-mfpmath=sse"
CPPFLAGS:append = " -Wno-error=pointer-sign -Wno-error=incompatible-pointer-types -fno-strict-aliasing"

export INCDIR = "${STAGING_INCDIR}"
export LIBDIR = "${STAGING_LIBDIR}"
export ARCH = "${TARGET_ARCH}"

# should be also arm compatible but untested
COMPATIBLE_HOST = '(x86_64.*|i686)-linux'

inherit gnu-efi

do_configure[depends] += "virtual/secure-boot-certificates:do_deploy"
do_configure[recideptask] += "virtual/secure-boot-certificates:do_deploy"
do_configure:append() {
	cp ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.crt \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.key \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.crt \
	${DEPLOY_DIR_IMAGE}/secure-boot-certificates/shim.key \
	.
	openssl pkcs12 -export -in shim.crt -inkey shim.key -out shim.p12 -passout pass:

	# X509 DER form
	for name in shim yocto; do
		openssl x509 -outform DER -in "${name}.crt" -out "${name}.cer"
	done

	# ese sbat marker append, should really be in UTF-8 specifically
	mkdir -p ${B}/data
	echo 'shim.ese,1,ESE,${PN},${PV},https://github.com/intel/iotg-yocto-ese-main' > ${B}/data/sbat.ese.csv

	# Makefile doesn't have an explicit dependencies
	# force invalidate
	rm -f shim.o cert.o
}

EXTRA_OEMAKE = "VENDOR_CERT_FILE=yocto.cer CROSS_COMPILE=${TARGET_PREFIX} CC="${CCLD}" \
ENABLE_SHIM_CERT=1 ENABLE_SBSIGN=1 ENABLE_HTTPBOOT=1 ${SHIM_DEFAULT_LOADER} EFI_CC="${CCLD}" \
EFI_HOSTCC="${CCLD}" TOPDIR="${S}/" BUILDDIR="${B}/" -I "${B}" -f "${S}/Makefile""

do_compile:prepend() {
	unset CFLAGS LDFLAGS LIBDIR prefix

	# native tool used during install
	${BUILD_CCLD} -o "${B}/buildid" ${BUILD_CPPFLAGS} ${BUILD_LDFLAGS} "${S}/buildid.c" -lelf
}

addtask deploy after do_install before do_build

do_install() {
	oe_runmake install-as-data DESTDIR=${D}
}

do_deploy:class-native() {
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

FILES:${PN} += "${datadir}"

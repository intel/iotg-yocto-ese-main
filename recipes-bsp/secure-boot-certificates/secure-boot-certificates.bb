# This should not be installed into an actual production system
# Mainly for bitbake build-time signing and debugging only

DESCRIPTION = "Secure Boot Certificates"
HOMEPAGE = ""
LICENSE = "MIT"
SECTION = "security"
DEPENDS = "openssl-native"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit allarch deploy
PROVIDES = "virtual/secure-boot-certificates"
RPROVIDES_${PN} = "virtual/secure-boot-certificates"
S = "${WORKDIR}"
# cert-to-efi-sig-list/sign-efi-sig-list from efitools is not available yet
do_compile(){
  for name in db KEK PK shim yocto; do
    openssl req -newkey "${SECURE_BOOT_CERT_GEN_ALGO}:${SECURE_BOOT_CERT_GEN_LENGTH}" -nodes -keyout "${name}.key" -new -x509 -"${SECURE_BOOT_CERT_GEN_HASH}" -days 3650 -subj "${SECURE_BOOT_CERT_GEN_SUBJECT}" -addext "nsComment=${PN} ${name} certificate" -out "${name}.crt"
  done
}

do_install(){
  install -m 755 -d ${D}/${datadir}/secure-boot-certificates
  for name in db KEK PK shim yocto; do
    install -m 644 ${name}.key ${D}/${datadir}/secure-boot-certificates
    install -m 644 ${name}.crt ${D}/${datadir}/secure-boot-certificates
  done
}

do_deploy(){
  install -m 755 -d ${DEPLOYDIR}/secure-boot-certificates
  for name in db KEK PK shim yocto; do
    install -m 644 ${name}.key ${DEPLOYDIR}/secure-boot-certificates
    install -m 644 ${name}.crt ${DEPLOYDIR}/secure-boot-certificates
  done
}

addtask do_deploy before do_build after do_compile

FILES_${PN} = "${datadir}"

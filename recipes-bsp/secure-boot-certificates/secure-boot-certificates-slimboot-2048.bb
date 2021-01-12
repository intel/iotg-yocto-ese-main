# This should not be installed into an actual production system
# Mainly for bitbake build-time signing and debugging only

DESCRIPTION = "Secure Boot Certificates 2048 bit keys for slimboot"
HOMEPAGE = ""
LICENSE = "MIT"
SECTION = "security"
DEPENDS = "openssl-native"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit allarch deploy
PROVIDES = "virtual/secure-boot-certificates-slimboot-2048"
RPROVIDES_${PN} = "virtual/secure-boot-certificates-slimboot-2048"
S = "${WORKDIR}"
# cert-to-efi-sig-list/sign-efi-sig-list from efitools is not available yet
do_compile(){
  for name in SigningKey; do
    openssl req -newkey rsa:2048 -nodes -keyout "${name}.key" -new -x509 -sha256 -days 3650 -subj "${SECURE_BOOT_CERT_GEN_SUBJECT}" -out "${name}.crt"
    openssl x509 -outform DER -in "${name}.crt" -out "${name}.cer"
    # slimboot tools uses PKCS#1, not PKCS#8
    openssl rsa -in "${name}.key" -out "${name}.pem"
  done
}

do_install(){
  install -m 755 -d ${D}/${datadir}/secure-boot-certificates-slimboot-2048
  for name in SigningKey; do
    for format in key pem crt cer; do
      install -m 644 ${name}.${format} ${D}/${datadir}/secure-boot-certificates-slimboot-2048
    done
  done
}

do_deploy(){
  install -m 755 -d ${DEPLOYDIR}/secure-boot-certificates-slimboot-2048
  for name in SigningKey; do
    for format in key pem crt cer; do
      install -m 644 ${name}.${format} ${DEPLOYDIR}/secure-boot-certificates-slimboot-2048
    done
  done
}

addtask do_deploy before do_build after do_compile

FILES_${PN} = "${datadir}"

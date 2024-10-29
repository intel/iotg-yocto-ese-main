# This should not be installed into an actual production system
# Mainly for bitbake build-time signing and debugging only

DESCRIPTION = "Secure Boot Certificates 2048 bit keys for slimboot"
HOMEPAGE = ""
LICENSE = "MIT"
SECTION = "security"
DEPENDS = "openssl-native"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SBLIMAGE_CERT_GEN_ALGO ?= "rsa"
SBLIMAGE_BOOT_CERT_GEN_LENGTH ?= "4096"
SBLIMAGE_BOOT_CERT_GEN_HASH ?= "sha256"
SBLIMAGE_BOOT_CERT_GEN_SUBJECT ?= "/CN=BSP Signing Key/"

inherit allarch deploy
PROVIDES = "virtual/secure-boot-certificates-slimboot"
RPROVIDES:${PN} = "virtual-secure-boot-certificates-slimboot"
S = "${WORKDIR}"
# cert-to-efi-sig-list/sign-efi-sig-list from efitools is not available yet
do_compile(){
  for name in SigningKey; do
    openssl req -newkey "${SBLIMAGE_CERT_GEN_ALGO}:${SBLIMAGE_BOOT_CERT_GEN_LENGTH}" -nodes -keyout "${name}.key" -new -x509 -${SBLIMAGE_BOOT_CERT_GEN_HASH} -days 3650 -subj "${SBLIMAGE_BOOT_CERT_GEN_SUBJECT}" -addext "nsComment=${PN} ${name} certificate" -out "${name}.crt"
    openssl x509 -outform DER -in "${name}.crt" -out "${name}.cer"
    # slimboot tools uses PKCS#1, not PKCS#8
    openssl rsa -traditional -in "${name}.key" -out "${name}.pem"
  done
}

do_install(){
  install -m 755 -d ${D}/${datadir}/secure-boot-certificates-slimboot
  for name in SigningKey; do
    for format in key pem crt cer; do
      install -m 644 ${name}.${format} ${D}/${datadir}/secure-boot-certificates-slimboot
    done
  done
}

do_deploy(){
  install -m 755 -d ${DEPLOYDIR}/secure-boot-certificates-slimboot
  for name in SigningKey; do
    for format in key pem crt cer; do
      install -m 644 ${name}.${format} ${DEPLOYDIR}/secure-boot-certificates-slimboot
    done
  done
}

addtask do_deploy before do_build after do_compile

FILES:${PN} = "${datadir}"


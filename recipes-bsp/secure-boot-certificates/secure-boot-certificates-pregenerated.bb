# This should not be installed into an actual production system
# Mainly for bitbake build-time signing and debugging only

FILESEXTRAPATHS_prepend := "${PREGENERATED_SIGNING_KEY_DIR}:"
DESCRIPTION = "Secure Boot Certificates"
LICENSE = "MIT"
SECTION = "security"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = " \
           file://${PREGENERATED_SIGNING_KEY_PK_KEY} file://${PREGENERATED_SIGNING_KEY_PK_CRT} \
           file://${PREGENERATED_SIGNING_KEY_KEK_KEY} file://${PREGENERATED_SIGNING_KEY_KEK_CRT} \
           file://${PREGENERATED_SIGNING_KEY_DB_KEY} file://${PREGENERATED_SIGNING_KEY_DB_CRT} \
           file://${PREGENERATED_SIGNING_KEY_MOK_KEY} file://${PREGENERATED_SIGNING_KEY_MOK_CRT} \
           file://${PREGENERATED_SIGNING_KEY_SHIM_KEY} file://${PREGENERATED_SIGNING_KEY_SHIM_CRT}"
DEPENDS += "openssl-native"
inherit allarch deploy
PROVIDES = "virtual/secure-boot-certificates"
RPROVIDES_${PN} = "virtual/secure-boot-certificates"
# normalize names
do_configure(){
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_PK_KEY} PK.key
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_PK_CRT} PK.crt
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_KEK_KEY} KEK.key
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_KEK_CRT} KEK.crt
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_DB_KEY} db.key
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_DB_CRT} db.crt
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_MOK_KEY} yocto.key
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_MOK_CRT} yocto.crt
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_SHIM_KEY} shim.key
  cp ${WORKDIR}/${PREGENERATED_SIGNING_KEY_SHIM_CRT} shim.crt
}

# sanity check
do_compile(){
  for name in PK KEK db shim yocto; do
    openssl pkey -pubout -in ${name}.key > ${name}.key.pub
    openssl x509 -noout -pubkey -in ${name}.crt > ${name}.crt.pub
    cmp ${name}.key.pub ${name}.crt.pub
  done
}

do_install(){
  install -m 755 -d ${D}/${datadir}/secure-boot-certificates
  for name in PK KEK db shim yocto; do
    install -m 644 ${name}.key ${D}/${datadir}/secure-boot-certificates
    install -m 644 ${name}.crt ${D}/${datadir}/secure-boot-certificates
  done
}

do_deploy(){
  install -m 755 -d ${DEPLOYDIR}/secure-boot-certificates
  for name in PK KEK db shim yocto; do
    install -m 644 ${name}.key ${DEPLOYDIR}/secure-boot-certificates
    install -m 644 ${name}.crt ${DEPLOYDIR}/secure-boot-certificates
  done
}
addtask do_deploy before do_build after do_compile

FILES_${PN} = "${datadir}"

# suppress warnings with /dev/null, install would fail if these are not set to point to the key files
PREGENERATED_SIGNING_KEY_DIR ??= "/dev"
PREGENERATED_SIGNING_KEY_PK_KEY ??= "null"
PREGENERATED_SIGNING_KEY_PK_CRT ??= "null"
PREGENERATED_SIGNING_KEY_KEK_KEY ??= "null"
PREGENERATED_SIGNING_KEY_KEK_CRT ??= "null"
PREGENERATED_SIGNING_KEY_DB_KEY ??= "null"
PREGENERATED_SIGNING_KEY_DB_CRT ??= "null"
PREGENERATED_SIGNING_KEY_MOK_KEY ??= "null"
PREGENERATED_SIGNING_KEY_MOK_CRT ??= "null"
PREGENERATED_SIGNING_KEY_SHIM_KEY ??= "null"
PREGENERATED_SIGNING_KEY_SHIM_CRT ??= "null"

# This should not be installed into an actual production system
# Mainly for bitbake build-time signing and debugging only

DESCRIPTION = "Secure Boot Certificates 3072 bit keys for slimboot"
HOMEPAGE = ""
LICENSE = "MIT"
SECTION = "security"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
INHIBIT_DEFAULT_DEPS = "1"

inherit allarch deploy
PROVIDES = "virtual/secure-boot-certificates-slimboot-3072"
RPROVIDES_${PN} = "virtual/secure-boot-certificates-slimboot-3072"
S = "${WORKDIR}"

SRC_URI = "file://${DOWNLOAD_KEY};downloadfilename=${BPN}.pem"

do_install(){
  install -m 755 -d ${D}/${datadir}/secure-boot-certificates-slimboot-3072
  install -m 644 "${WORKDIR}/${DOWNLOAD_KEY}" ${D}/${datadir}/secure-boot-certificates-slimboot-3072/SigningKey.pem
}

do_deploy(){
  install -m 755 -d ${DEPLOYDIR}/secure-boot-certificates-slimboot-3072
  install -m 644 "${WORKDIR}/${DOWNLOAD_KEY}" ${DEPLOYDIR}/secure-boot-certificates-slimboot-3072/SigningKey.pem
}

addtask do_deploy before do_build after do_compile

FILES_${PN} = "${datadir}"
# Should be set by user to a valid key
PREGENERATED_SIGNING_KEY_SLIMBOOT_KEY_SHA384 ??= "/dev/null"
SBL_KEY_PATH = "${PREGENERATED_SIGNING_KEY_SLIMBOOT_KEY_SHA384}"

require ./secure-boot-certificates-slimboot-pregenerated.inc

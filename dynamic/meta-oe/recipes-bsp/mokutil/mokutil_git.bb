SUMMARY = "The utility to manipulate machines owner keys which managed in shim"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/lcp/mokutil.git;protocol=https;tag=0.6.0"
S = "${WORKDIR}/git"
PV = "0.6.0+git${SRCPV}"

inherit autotools pkgconfig bash-completion

DEPENDS += "efivar gnu-efi openssl virtual/crypt keyutils"

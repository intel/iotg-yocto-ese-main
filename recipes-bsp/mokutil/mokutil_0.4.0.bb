SUMMARY = "The utility to manipulate machines owner keys which managed in shim"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "git://github.com/lcp/mokutil.git;protocol=https;nobranch=1"

S = "${WORKDIR}/git"
SRCREV = "18e5eb052116221197a70e788afc414c1e1f4bb7"

inherit autotools pkgconfig bash-completion

DEPENDS += "efivar gnu-efi openssl virtual/crypt"

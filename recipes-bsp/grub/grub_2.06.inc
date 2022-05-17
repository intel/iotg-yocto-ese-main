FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SUMMARY = "GRUB2 is the next-generation GRand Unified Bootloader"

DESCRIPTION = "GRUB2 is the next generaion of a GPLed bootloader \
intended to unify bootloading across x86 operating systems. In \
addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."

HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
SRC_URI = "git://github.com/rhboot/grub2.git;protocol=https;nobranch=1;name=grub"
SRCREV_grub = "7c993f62b426daba88406c9347f31e6b681d9783"

SRC_URI:append = " git://git.savannah.gnu.org/git/gnulib.git;protocol=https;name=gnulib;destsuffix=git/gnulib;nobranch=1"
SRCREV_gnulib = "9f48fb992a3d7e96610c4ce8be969cff2d61a01b"

SRCREV_FORMAT = "grub_gnulib"
S = "${WORKDIR}/git"
PR = "r40+${SRCPV}"
PV = "2.06"
SRC_URI:append = " \
  file://bootstrap.conf;subdir=. \
  file://bootstrap;subdir=. \
  file://gitignore \
  file://strtoull_test.c;subdir=grub-core/tests \
  http://unifoundry.com/pub/unifont/unifont-13.0.06/font-builds/unifont-13.0.06.pcf.gz;downloadfilename=unifont.pcf.gz;subdir=.;unpack=0;name=unifont \
  file://0001-bootp-fix-bootp-compile-if-EFI-disabled.patch \
  file://0001-configure.ac-check-if-fno-trampolines-is-supported.patch;apply=${GRUB_NATIVE_PATCH} \
"

GRUB_NATIVE_PATCH = "no"
GRUB_NATIVE_PATCH:class-native = "yes"

SRC_URI[unifont.md5sum] = "4fbc33c8d8d25985f1bcdd4d28bc2420"
SRC_URI[unifont.sha256sum] = "aea0ea746353c6c2b5b8874e65facd087bb0346ec198993f8aaaa21eea040378"

DEPENDS = "flex-native bison-native gettext-native"

COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*)-(linux.*|freebsd.*)'
COMPATIBLE_HOST:armv7a = 'null'
COMPATIBLE_HOST:armv7ve = 'null'

# configure.ac has code to set this automagically from the target tuple
# but the OE freeform one (core2-foo-bar-linux) don't work with that.

GRUBPLATFORM:arm = "efi"
GRUBPLATFORM:aarch64 = "efi"
GRUBPLATFORM ??= "pc"

inherit autotools gettext texinfo pkgconfig

EXTRA_OECONF = "--with-platform=${GRUBPLATFORM} \
                --disable-grub-mkfont \
                --program-prefix="" \
                --enable-liblzma=no \
                --enable-libzfs=no \
                --enable-largefile \
                --disable-werror \
                --with-utils=host \
                --disable-rpm-sort \
                --disable-grub-themes \
"

export HOST_LDFLAGS = "${LDFLAGS}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[grub-mount] = "--enable-grub-mount,--disable-grub-mount,fuse"
PACKAGECONFIG[device-mapper] = "--enable-device-mapper,--disable-device-mapper,libdevmapper"

CFLAGS:append:class-native = " -I${STAGING_INCDIR_NATIVE}"

export PYTHON = "python3"

RDEPENDS:${PN}:class-native = ""

do_prepatch(){
	cp -f ${WORKDIR}/gitignore ${S}/.gitignore
}
do_postpatch(){
	sed -e 's@-specs=/usr/lib/rpm/redhat/redhat-hardened-cc1 -specs=/usr/lib/rpm/redhat/redhat-annobin-cc1@@g' -i ${S}/gnulib/gnulib-tool
	sed -e 's@ -Werror @@g' -i ${S}/gnulib/gnulib-tool
	sed -e 's@-Wmissing-include-dirs@@g' -i ${S}/gnulib/m4/manywarnings-c++.m4
	sed -e 's@-Wmissing-include-dirs@@g' -i ${S}/gnulib/m4/manywarnings.m4
}
do_patch[prefuncs] += "do_prepatch"
do_patch[postfuncs] += "do_postpatch"

# avoid bootstrapping twice, causing problems with missing files
do_configure:prepend(){
	( cd ${S}
	chmod +x ${S}/bootstrap
	[ ! -f ${S}/.bootstrap_done ] && ${S}/bootstrap --no-git --gnulib-srcdir="${S}/gnulib"
        touch ${S}/.bootstrap_done )
}

# fix warnings with CFLAGS
TUNE_CCARGS:remove = "-mfpmath=sse"
EXTRA_OECONF:append:x86-64 = " TARGET_LDLFAGS="${LDFLAGS} -no-pie""

do_install:append() {
    # Remove build host references...
    find "${D}" -name modinfo.sh -type f -exec \
        sed -i \
        -e 's,--sysroot=${STAGING_DIR_TARGET},,g' \
        -e 's|${DEBUG_PREFIX_MAP}||g' \
        -e 's:${RECIPE_SYSROOT_NATIVE}::g' \
        {} +
    # remove empty usr/lib
    if test "${baselib}" != "lib"; then
      rm -rf ${D}${prefix}/lib
    fi
}

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SUMMARY = "GRUB2 is the next-generation GRand Unified Bootloader"

DESCRIPTION = "GRUB2 is the next generaion of a GPLed bootloader \
intended to unify bootloading across x86 operating systems. In \
addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."

HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
SRC_URI = "git://github.com/rhboot/grub2.git;protocol=https;nobranch=1;name=grub"
SRCREV_grub = "73a0a9bc0fca27530ce83607e70750d7ecb42cb6"

SRC_URI_append = " git://github.com/rhboot/gnulib.git;protocol=https;name=gnulib;destsuffix=git/gnulib;nobranch=1"
SRCREV_gnulib = "0e9febb5e173f1dc85e5993a72aa886b431ff32f"

SRCREV_FORMAT = "grub_gnulib"
S = "${WORKDIR}/git"
PR = "r35+${SRCPV}"
SRC_URI_append = " \
  file://bootstrap.conf;subdir=. \
  file://bootstrap;subdir=. \
  file://gitignore \
  file://strtoull_test.c;subdir=grub-core/tests \
  http://unifoundry.com/unifont-5.1.20080820.pcf.gz;downloadfilename=unifont.pcf.gz;subdir=.;unpack=0;name=unifont \
  file://0001-grub-rpm-sort-make-rpm-sort-actually-optional.patch \
  file://0001-bootp-fix-bootp-compile-if-EFI-disabled.patch \
  file://0001-configure.ac-check-if-fno-trampolines-is-supported.patch;apply=${GRUB_NATIVE_PATCH} \
"

GRUB_NATIVE_PATCH = "no"
GRUB_NATIVE_PATCH_class-native = "yes"

SRC_URI[unifont.md5sum] = "8c28087c5fcb3188f1244b390efffdbe"
SRC_URI[unifont.sha256sum] = "122b9c470f29b70223b0e07404a6dfa7f339fcfa6ae74c024f478945af7a9a63"

DEPENDS = "flex-native bison-native gettext-native"

COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*)-(linux.*|freebsd.*)'
COMPATIBLE_HOST_armv7a = 'null'
COMPATIBLE_HOST_armv7ve = 'null'

# configure.ac has code to set this automagically from the target tuple
# but the OE freeform one (core2-foo-bar-linux) don't work with that.

GRUBPLATFORM_arm = "efi"
GRUBPLATFORM_aarch64 = "efi"
GRUBPLATFORM ??= "pc"

inherit autotools gettext texinfo

EXTRA_OECONF = "--with-platform=${GRUBPLATFORM} \
                --disable-grub-mkfont \
                --program-prefix="" \
                --enable-liblzma=no \
                --enable-libzfs=no \
                --enable-largefile \
                --disable-werror \
                --with-utils=host \
                --disable-rpm-sort \
"

export HOST_LDFLAGS = "${LDFLAGS}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[grub-mount] = "--enable-grub-mount,--disable-grub-mount,fuse"
PACKAGECONFIG[device-mapper] = "--enable-device-mapper,--disable-device-mapper,libdevmapper"

CFLAGS_append_class-native = " -I${STAGING_INCDIR_NATIVE}"

export PYTHON = "python3"

RDEPENDS_${PN}_class-native = ""

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
do_configure_prepend(){
	( cd ${S}
	chmod +x ${S}/bootstrap
	[ ! -f ${S}/.bootstrap_done ] && ${S}/bootstrap --no-git --gnulib-srcdir="${S}/gnulib"
        touch ${S}/.bootstrap_done )
}

# fix warnings with CFLAGS
TUNE_CCARGS_remove = "-mfpmath=sse"
EXTRA_OECONF_append_x86-64 = " TARGET_LDLFAGS="${LDFLAGS} -no-pie""

do_install_append() {
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

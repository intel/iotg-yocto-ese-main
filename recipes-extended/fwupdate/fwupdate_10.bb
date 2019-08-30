SUMMARY = "Tools for using the ESRT and UpdateCapsule() to apply firmware updates"
PR = "r2"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6d7bfc784f89da02599036c034adfb34"
DEPENDS = " \
    efibootmgr \
    efivar (>= 0.34) \
    gnu-efi \
"

SRC_URI = "git://github.com/rhboot/fwupdate.git \
           file://0001-Makefile-fix.patch \
           file://0001-efi-Makefile-remove-standard-system-header-file-dire.patch \
           "
SRCREV = "82453e7008ed24ed541c0a5fea1e9df190329409"
S = "${WORKDIR}/git"

inherit gettext pkgconfig

# There is -I/usr/include/efi/ -I/usr/include/efi/$(ARCH)/ in efi/Makefile,
# but those seem to be ignored due to -nostdinc (considered "system includes"
# because of the path by gcc?). We need to specify them with the full, non-standard
# path.
#
# GNUEFIDIR and LIBDIR below includes the sysroot for the same reason.
CFLAGS += "-I${RECIPE_SYSROOT}${includedir}/efi -I${RECIPE_SYSROOT}${includedir}/efi/${ARCH} -I${RECIPE_SYSROOT}${includedir}/efivar"

# Does not work in combination with the -mno-sse that is needed
# for the EFI app.
CC_remove = "-mfpmath=sse"

EXTRA_OEMAKE += " \
    CC='${CC}' \
    LD='${LD}' \
    CPP='${CPP}' \
    OBJCOPY='${OBJCOPY}' \
    CFLAGS='${CFLAGS}' \
    libdir=${libdir}/ \
    libdatadir=${nonarch_base_libdir} \
    libexecdir=${libexecdir}/ \
    datadir=${datadir}/cache/ \
    localedir=${datadir}/locale/ \
    LIBDIR=${RECIPE_SYSROOT}${libdir}/ \
    GNUEFIDIR=${RECIPE_SYSROOT}${libdir}/ \
    EFIDIR='boot' \
"

# libsmbios currently has no recipe. fwupdate compiles also without it.
EXTRA_OEMAKE += "HAVE_LIBSMBIOS=no"

# Resolves "include/fwup.h:20:10: fatal error: fwup-version.h: No such file or directory" error
PARALLEL_MAKE = "-j1"

do_install () {
    oe_runmake install DESTDIR=${D}
    rm -f ${D}/${debuglibdir}/boot/efi/EFI/refkit/fwupx64.efi.debug

    if [ ! -f ${D}/boot/efi/EFI/boot/fwupx64.efi ]; then
        cp efi/fwupx64.efi ${D}/boot/efi/EFI/boot/
    fi
}

PACKAGES += "${PN}-bash-completion"
FILES_${PN}-bash-completion = "${datadir}/bash-completion/completions/"
RDEPENDS_${PN}-bash-completion = "bash"

# Packaging these files does not help much. We lack conventions and
# a postinst for installing them into the actual EFI system partition.
# TODO: deploy the files into the image dir instead and build images
# with them via the EFI_PROVIDER mechanism.
PACKAGES += "${PN}-boot"
FILES_${PN}-boot += "/boot"

FILES_${PN} += " \
    ${datadir}/cache/fwupdate \
    ${systemd_system_unitdir} \
"

# The EFI app doesn't pass the test because it needs to be (?) compiled
# differently. We therefore disable the check to avoid:
# do_package_qa: QA Issue: No GNU_HASH in the elf binary: '.../packages-split/fwupdate-dbg/usr/lib/debug/boot/efi/EFI/refkit/fwupx64.efi.debug' [ldflags]
WARN_QA_remove = "ldflags"
ERROR_QA_remove = "ldflags"

# Copy for Wic image
do_deploy() {
	install -d ${DEPLOYDIR}/${PN}-boot
	cp -r ${D}/boot ${DEPLOYDIR}/${PN}-boot/
}

inherit deploy
addtask deploy after do_install before do_build


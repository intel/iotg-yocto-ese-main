SUMMARY = "Tools for using the ESRT and UpdateCapsule() to apply firmware updates"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6d7bfc784f89da02599036c034adfb34"
DEPENDS = " \
    efibootmgr \
    efivar (>= 0.34) \
    gnu-efi \
"

SRC_URI = "git://github.com/rhboot/fwupdate.git \
           file://0001-efi-Makefile-remove-standard-system-header-file-dire.patch \
           file://132.patch \
           file://0001-efi-Makefile-use-gnu-efi-provided-linker-script.patch \
           file://0002-fix-compiling-with-efivar-version-38.patch \
           "
SRCREV = "6101b6b304da06644bd7a90444f729d0fc44940e"
S = "${WORKDIR}/git"

inherit gettext pkgconfig bash-completion
RDEPENDS:${PN}-bash-completion:append = " ${PN} bash"

inherit gnu-efi
CFLAGS += "-I${RECIPE_SYSROOT}${includedir}/efi -I${RECIPE_SYSROOT}${includedir}/efi/${GNU_EFI_ARCH} -I${RECIPE_SYSROOT}${includedir}/efivar"

# Does not work in combination with the -mno-sse that is needed
# for the EFI app.
CC:remove = "-mfpmath=sse"

EXTRA_OEMAKE += " \
    CC='${CC}' \
    LD='${LD}' \
    CPP='${CPP}' \
    OBJCOPY='${OBJCOPY}' \
    CFLAGS='${CFLAGS}' \
    libdir=${libdir}/ \
    libdatadir=${nonarch_base_libdir} \
    libexecdir=${libexecdir}/ \
    datadir=${datadir}/ \
    localedir=${datadir}/locale/ \
    LIBDIR=${STAGING_LIBDIR}/ \
    GNUEFIDIR=${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH} \
    EFIDIR='BOOT' \
"

# libsmbios currently has no recipe. fwupdate compiles also without it.
EXTRA_OEMAKE += "HAVE_LIBSMBIOS=no"

# Resolves "include/fwup.h:20:10: fatal error: fwup-version.h: No such file or directory" error
PARALLEL_MAKE = "-j1"

do_install () {
    oe_runmake install DESTDIR=${D}
    rm -f ${D}/${debuglibdir}/boot/efi/EFI/refkit/fwup${GNU_EFI_ARCH}.efi.debug

    if [ ! -f ${D}/boot/efi/EFI/BOOT/fwup${GNU_EFI_ARCH}.efi ]; then
        cp efi/fwup${GNU_EFI_ARCH}.efi ${D}/boot/efi/EFI/BOOT/
    fi
}

# Packaging these files does not help much. We lack conventions and
# a postinst for installing them into the actual EFI system partition.
# TODO: deploy the files into the image dir instead and build images
# with them via the EFI_PROVIDER mechanism.
PACKAGES += "${PN}-boot"
FILES:${PN}-boot += "/boot"

FILES:${PN} += " \
    ${datadir}/cache/fwupdate \
    ${systemd_system_unitdir} \
"

# The EFI app doesn't pass the test because it needs to be (?) compiled
# differently. We therefore disable the check to avoid:
# do_package_qa: QA Issue: No GNU_HASH in the elf binary: '.../packages-split/fwupdate-dbg/usr/lib/debug/boot/efi/EFI/refkit/fwupx64.efi.debug' [ldflags]
WARN_QA:remove = "ldflags"
ERROR_QA:remove = "ldflags"

# Copy for Wic image
do_deploy() {
	install -d ${DEPLOYDIR}/${PN}-boot
	cp -r ${D}/boot ${DEPLOYDIR}/${PN}-boot/
}

inherit deploy
addtask deploy after do_install before do_build


# fix with fedora gnu-efi
inherit gnu-efi
EXTRA_OEMESON_remove = "-Defi-ldsdir=${STAGING_LIBDIR} -Defi-libdir=${STAGING_LIBDIR}"
EXTRA_OEMESON_append = "-Defi-libdir=${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH}"

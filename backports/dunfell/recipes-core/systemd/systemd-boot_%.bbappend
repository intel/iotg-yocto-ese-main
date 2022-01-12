# fix with fedora gnu-efi
inherit gnu-efi
EXTRA_OEMESON:remove = "-Defi-ldsdir=${STAGING_LIBDIR} -Defi-libdir=${STAGING_LIBDIR}"
EXTRA_OEMESON:append = "-Defi-ldsdir=${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH} -Defi-libdir=${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH}"

inherit gnu-efi
EXTRA_OEMESON:remove = "-Defi-ldsdir=${STAGING_LIBDIR} -Defi-libdir=${STAGING_LIBDIR}"
EXTRA_OEMESON:append = "-Defi-libdir=${STAGING_LIBDIR}/gnuefi/${GNU_EFI_ARCH}"

# fix build against new fedora gnu-efi
SRCREV_sbsigntools  = "10ffaae22bd07dc482d202fda960336bac6dd351"

inherit gnu-efi
export CRTPATH="${STAGING_LIBDIR_NATIVE}/gnuefi/${GNU_EFI_ARCH}"

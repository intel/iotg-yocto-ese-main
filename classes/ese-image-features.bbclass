# image post processing (to be used with IMGCLASSES_append)
# these are listed in order of execution by appending IMAGE_PREPROCESS_COMMAND
require ${@bb.utils.contains('MACHINE_FEATURES', 'efi', 'conf/image-uefi.conf', '', d)}

# should grub.cfg into grub efi?
ESE_EMBED_GRUB_CFG ??= "1"
# root of EFI system partition as mounted on Linux
EFI_PREFIX ??= "/boot/efi"

# extensions
IMAGE_FEATURES[validitems] += "ese-grub-image ese-mender-persistent ese-shim-image efi-lockdown"
ESE_IMAGE_PREPROCESS ??= ""
ESE_IMAGE_POSTPROCESS ??= ""

inherit ${ESE_IMAGE_PREPROCESS}

# put build id/date data (os release/mender release date info files)
#inherit ${@bb.utils.contains_any('DISTRO_FEATURES', [ 'build-release-id' ], 'build-release-id', '', d)}

# ptcm
inherit ${@bb.utils.contains('PTCM_INSTALL', '1', 'ese-ptcm-image', '', d)}

# efi secure boot lockdown mechanism from efitools
inherit ${@bb.utils.contains_any('IMAGE_FEATURES', [ 'efi-lockdown' ], 'ese-uefi-lockdown', '', d)}

# mkimage generated grub EFI
inherit ${@bb.utils.contains_any('IMAGE_FEATURES', [ 'ese-grub-image' ], 'ese-grub-image', '', d)}

# install shim/MokManager
inherit ${@bb.utils.contains_any('IMAGE_FEATURES', [ 'ese-shim-image' ], 'ese-shim-image', '', d)}

# finalize/select EFI boot loader
inherit ${@bb.utils.contains('MACHINE_FEATURES', 'efi', 'ese-uefi-finalize', '', d)}

# secure boot signing for files under /boot (only UEFI shim+MOK style for now)
inherit ${@bb.utils.contains_any('SECUREBOOT', [ 'true' ], 'ese-uefi-secure-boot-signing', '', d)}

# slimboot packaging helpers
#inherit ${@bb.utils.contains_any('SLIMBOOT', [ 'simple' ], 'slimboot-kernel', '', d)}

# mender /data partitioning fix up
inherit ${@bb.utils.contains_any('IMAGE_FEATURES', [ 'ese-mender-persistent' ], 'mender-persistent', '', d)}

inherit ${ESE_IMAGE_POSTPROCESS}

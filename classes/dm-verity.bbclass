# Class to check if dm-verity feature is enabled/included in
# distro configuration and if included, then include initramfs
# image to the build, bundle it with the kernel and enable
# read-only rootfs. This class will need to be inherited by
# the distro configuration.

python(){
        if bb.utils.contains('DM_VERITY', 'true', '1', '', d) == "1":
                d.setVar("INITRAMFS_IMAGE", "initramfs-dm-verity")
                d.setVar("INITRAMFS_IMAGE_BUNDLE", "1")
                d.appendVar("IMAGE_FEATURES", " read-only-rootfs")

                # u-boot inserted with public certificate
                # for verifying signature of the kernel,
                # conditional upon dm-verity being enabled.
                d.setVar("UBOOT_BINARY", "u-boot-with-pub-cert.bin")

                if "initramfs" not in d.getVar("PN"):
                        # For wic-based image generation.
                        d.setVar('IMAGE_FSTYPES', d.getVar('IMAGE_FSTYPES') + ' wic')
                        d.setVar("WKS_FILE", "dm-verity-image-gen.wks.in")
                        d.appendVar("WKS_FILE_DEPENDS", " coreutils-native u-boot-tools-native cryptsetup-native openssl-native")
                        d.appendVar("WICVARS", " FIT_KEYS_DIR ITS_FILE DM_VERITY")
                        d.setVar("IMAGE_BOOT_FILES", " Image-initramfs-${MACHINE}.bin;Image")
}

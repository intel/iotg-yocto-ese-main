SUMMARY = "GRUB2 is the next-generation GRand Unified Bootloader"

DESCRIPTION = "GRUB2 is the next generaion of a GPLed bootloader \
intended to unify bootloading across x86 operating systems. In \
addition to loading the Linux kernel, it implements the Multiboot \
standard, which allows for flexible loading of multiple boot images."

HOMEPAGE = "http://www.gnu.org/software/grub/"
SECTION = "bootloaders"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PATCHTOOL = "git"
PR = "r94"
S = "${WORKDIR}/grub-${PV}"
SRC_URI  = "https://ftp.gnu.org/pub/gnu/grub/grub-${PV}.tar.xz \
file://release-to-master.patch \
file://0001-Add-support-for-Linux-EFI-stub-loading.patch \
file://0002-Rework-linux-command.patch \
file://0003-Rework-linux16-command.patch \
file://0004-Add-secureboot-support-on-efi-chainloader.patch \
file://0005-Make-any-of-the-loaders-that-link-in-efi-mode-honor-.patch \
file://0006-Handle-multi-arch-64-on-32-boot-in-linuxefi-loader.patch \
file://0007-re-write-.gitignore.patch \
file://0008-IBM-client-architecture-CAS-reboot-support.patch \
file://0009-for-ppc-reset-console-display-attr-when-clear-screen.patch \
file://0010-Disable-GRUB-video-support-for-IBM-power-machines.patch \
file://0011-Honor-a-symlink-when-generating-configuration-by-gru.patch \
file://0012-Move-bash-completion-script-922997.patch \
file://0013-Update-to-minilzo-2.08.patch \
file://0014-Allow-fallback-to-include-entries-by-title-not-just-.patch \
file://0015-Add-GRUB_DISABLE_UUID.patch \
file://0016-Make-exit-take-a-return-code.patch \
file://0017-Mark-po-exclude.pot-as-binary-so-git-won-t-try-to-di.patch \
file://0018-Make-efi-machines-load-an-env-block-from-a-variable.patch \
file://0019-DHCP-client-ID-and-UUID-options-added.patch \
file://0020-Fix-bad-test-on-GRUB_DISABLE_SUBMENU.patch \
file://0021-Add-support-for-UEFI-operating-systems-returned-by-o.patch \
file://0022-Migrate-PPC-from-Yaboot-to-Grub2.patch \
file://0023-Add-fw_path-variable-revised.patch \
file://0024-Pass-x-hex-hex-straight-through-unmolested.patch \
file://0025-Add-X-option-to-printf-functions.patch \
file://0026-Search-for-specific-config-file-for-netboot.patch \
file://0027-blscfg-add-blscfg-module-to-parse-Boot-Loader-Specif.patch \
file://0028-Add-devicetree-loading.patch \
file://0029-Don-t-write-messages-to-the-screen.patch \
file://0030-Don-t-print-GNU-GRUB-header.patch \
file://0031-Don-t-add-to-highlighted-row.patch \
file://0032-Message-string-cleanups.patch \
file://0033-Fix-border-spacing-now-that-we-aren-t-displaying-it.patch \
file://0034-Use-the-correct-indentation-for-the-term-help-text.patch \
file://0035-Indent-menu-entries.patch \
file://0036-Fix-margins.patch \
file://0037-Use-2-instead-of-1-for-our-right-hand-margin-so-line.patch \
file://0038-Enable-pager-by-default.-985860.patch \
file://0039-F10-doesn-t-work-on-serial-so-don-t-tell-the-user-to.patch \
file://0040-Don-t-say-GNU-Linux-in-generated-menus.patch \
file://0041-Don-t-draw-a-border-around-the-menu.patch \
file://0042-Use-the-standard-margin-for-the-timeout-string.patch \
file://0043-Add-.eh_frame-to-list-of-relocations-stripped.patch \
file://0044-Don-t-munge-raw-spaces-when-we-re-doing-our-cmdline-.patch \
file://0045-Don-t-require-a-password-to-boot-entries-generated-b.patch \
file://0046-Don-t-emit-Booting-.-message.patch \
file://0047-Replace-a-lot-of-man-pages-with-slightly-nicer-ones.patch \
file://0048-use-fw_path-prefix-when-fallback-searching-for-grub-.patch \
file://0049-Try-mac-guid-etc-before-grub.cfg-on-tftp-config-file.patch \
file://0050-Fix-convert-function-to-support-NVMe-devices.patch \
file://0051-Add-grub_util_readlink.patch \
file://0052-Make-editenv-chase-symlinks-including-those-across-d.patch \
file://0053-Generate-OS-and-CLASS-in-10_linux-from-etc-os-releas.patch \
file://0054-Minimize-the-sort-ordering-for-.debug-and-rescue-ker.patch \
file://0055-Try-prefix-if-fw_path-doesn-t-work.patch \
file://0056-Update-info-with-grub.cfg-netboot-selection-order-11.patch \
file://0057-Use-Distribution-Package-Sort-for-grub2-mkconfig-112.patch \
file://0058-Handle-rssd-storage-devices.patch \
file://0059-Make-grub2-mkconfig-construct-titles-that-look-like-.patch \
file://0060-Add-friendly-grub2-password-config-tool-985962.patch \
file://0061-tcp-add-window-scaling-support.patch \
file://0062-Fix-security-issue-when-reading-username-and-passwor.patch \
file://0063-Add-a-url-parser.patch \
file://0064-efinet-and-bootp-add-support-for-dhcpv6.patch \
file://0065-Add-grub-get-kernel-settings-and-use-it-in-10_linux.patch \
file://0066-Normalize-slashes-in-tftp-paths.patch \
file://0067-bz1374141-fix-incorrect-mask-for-ppc64.patch \
file://0068-Make-grub_fatal-also-backtrace.patch \
file://0069-Fix-up-some-man-pages-rpmdiff-noticed.patch \
file://0070-arm64-make-sure-fdt-has-address-cells-and-size-cells.patch \
file://0071-Make-our-info-pages-say-grub2-where-appropriate.patch \
file://0072-print-more-debug-info-in-our-module-loader.patch \
file://0073-macos-just-build-chainloader-entries-don-t-try-any-x.patch \
file://0074-grub2-btrfs-Add-ability-to-boot-from-subvolumes.patch \
file://0075-export-btrfs_subvol-and-btrfs_subvolid.patch \
file://0076-grub2-btrfs-03-follow_default.patch \
file://0077-grub2-btrfs-04-grub2-install.patch \
file://0078-grub2-btrfs-05-grub2-mkconfig.patch \
file://0079-grub2-btrfs-06-subvol-mount.patch \
file://0080-Fallback-to-old-subvol-name-scheme-to-support-old-sn.patch \
file://0081-Grub-not-working-correctly-with-btrfs-snapshots-bsc-.patch \
file://0082-Add-grub_efi_allocate_pool-and-grub_efi_free_pool-wr.patch \
file://0083-Use-grub_efi_.-memory-helpers-where-reasonable.patch \
file://0084-Add-PRIxGRUB_EFI_STATUS-and-use-it.patch \
file://0085-Don-t-use-dynamic-sized-arrays-since-we-don-t-build-.patch \
file://0086-don-t-ignore-const.patch \
file://0087-don-t-use-int-for-efi-status.patch \
file://0088-make-GRUB_MOD_INIT-declare-its-function-prototypes.patch \
file://0089-editenv-handle-relative-symlinks.patch \
file://0090-Make-libgrub.pp-depend-on-config-util.h.patch \
file://0091-Don-t-guess-boot-efi-as-HFS-on-ppc-machines-in-grub-.patch \
file://0092-20_linux_xen-load-xen-or-multiboot-2-modules-as-need.patch \
file://0093-Make-pmtimer-tsc-calibration-not-take-51-seconds-to-.patch \
file://0094-align-struct-efi_variable-better.patch \
file://0095-Add-BLS-support-to-grub-mkconfig.patch \
file://0096-Don-t-attempt-to-backtrace-on-grub_abort-for-grub-em.patch \
file://0097-Add-linux-and-initrd-commands-for-grub-emu.patch \
file://0098-Add-grub2-switch-to-blscfg.patch \
file://0099-Add-grub_debug_enabled.patch \
file://0100-make-better-backtraces.patch \
file://0101-normal-don-t-draw-our-startup-message-if-debug-is-se.patch \
file://0102-Work-around-some-minor-include-path-weirdnesses.patch \
file://0103-Make-it-possible-to-enabled-build-id-sha1.patch \
file://0104-Add-grub_qdprintf-grub_dprintf-without-the-file-line.patch \
file://0105-Make-a-gdb-dprintf-that-tells-us-load-addresses.patch \
file://0106-TPM-support.patch \
file://0107-Fixup-for-newer-compiler.patch \
file://0108-Don-t-attempt-to-export-the-start-and-_start-symbols.patch \
file://0109-Fixup-for-newer-compiler.patch \
file://0110-Use-xid-to-match-DHCP-replies.patch \
file://0111-Add-support-for-non-Ethernet-network-cards.patch \
file://0112-misc-fix-invalid-character-recongition-in-strto-l.patch \
file://0113-net-read-bracketed-ipv6-addrs-and-port-numbers.patch \
file://0114-bootp-New-net_bootp6-command.patch \
file://0115-efinet-UEFI-IPv6-PXE-support.patch \
file://0116-grub.texi-Add-net_bootp6-doument.patch \
file://0117-bootp-Add-processing-DHCPACK-packet-from-HTTP-Boot.patch \
file://0118-efinet-Setting-network-from-UEFI-device-path.patch \
file://0119-efinet-Setting-DNS-server-from-UEFI-protocol.patch \
file://0120-Fix-one-more-coverity-complaint.patch \
file://0121-Support-UEFI-networking-protocols.patch \
file://0122-AUDIT-0-http-boot-tracker-bug.patch \
file://0123-grub-core-video-efi_gop.c-Add-support-for-BLT_ONLY-a.patch \
file://0124-efi-uga-use-64-bit-for-fb_base.patch \
file://0125-EFI-console-Do-not-set-text-mode-until-we-actually-n.patch \
file://0126-EFI-console-Add-grub_console_read_key_stroke-helper-.patch \
file://0127-EFI-console-Implement-getkeystatus-support.patch \
file://0128-Make-grub_getkeystatus-helper-funtion-available-ever.patch \
file://0129-Accept-ESC-F8-and-holding-SHIFT-as-user-interrupt-ke.patch \
file://0130-grub-editenv-Add-incr-command-to-increment-integer-v.patch \
file://0131-Add-auto-hide-menu-support.patch \
file://0132-Output-a-menu-entry-for-firmware-setup-on-UEFI-FastB.patch \
file://0133-Add-grub-set-bootflag-utility.patch \
file://0134-docs-Add-grub-boot-indeterminate.service-example.patch \
file://0135-gentpl-add-disable-support.patch \
file://0136-gentpl-add-pc-firmware-type.patch \
file://0137-efinet-also-use-the-firmware-acceleration-for-http.patch \
file://0138-efi-http-Make-root_url-reflect-the-protocol-hostname.patch \
file://0139-Force-everything-to-use-python3.patch \
file://0140-Fix-an-8-year-old-typo.patch \
file://0141-autogen-don-t-run-autoreconf-in-the-topdir.patch \
file://0142-Make-it-so-we-can-tell-configure-which-cflags-utils-.patch \
file://0143-module-verifier-make-it-possible-to-run-checkers-on-.patch \
file://0144-grub-module-verifier-report-the-filename-or-modname-.patch \
file://0145-Rework-how-the-fdt-command-builds.patch \
file://0146-Disable-non-wordsize-allocations-on-arm.patch \
file://0147-strip-R-.note.gnu.property-at-more-places.patch \
file://0148-Prepend-prefix-when-HTTP-path-is-relative.patch \
file://0149-Make-linux_arm_kernel_header.hdr_offset-be-at-the-ri.patch \
file://0150-Make-grub_error-more-verbose.patch \
file://0151-Make-reset-an-alias-for-the-reboot-command.patch \
file://0152-EFI-more-debug-output-on-GOP-and-UGA-probing.patch \
file://0153-Add-a-version-command.patch \
file://0154-Add-more-dprintf-and-nerf-dprintf-in-script.c.patch \
file://0155-arm-arm64-loader-Better-memory-allocation-and-error-.patch \
file://0156-Try-to-pick-better-locations-for-kernel-and-initrd.patch \
file://0157-Attempt-to-fix-up-all-the-places-Wsign-compare-error.patch \
file://0158-Don-t-use-Wno-sign-compare-Wno-conversion-Wno-error-.patch \
file://0159-x86-efi-Use-bounce-buffers-for-reading-to-addresses-.patch \
file://0160-x86-efi-Re-arrange-grub_cmd_linux-a-little-bit.patch \
file://0161-x86-efi-Make-our-own-allocator-for-kernel-stuff.patch \
file://0162-x86-efi-Allow-initrd-params-cmdline-allocations-abov.patch \
file://0163-Fix-getroot.c-s-trampolines.patch \
file://0164-Do-not-allow-stack-trampolines-anywhere.patch \
file://0165-Reimplement-boot_counter.patch \
file://0166-Make-grub_strtoul-end-pointer-have-the-right-constif.patch \
file://0167-Fix-menu-entry-selection-based-on-ID-and-title.patch \
file://0168-Make-the-menu-entry-users-option-argument-to-be-opti.patch \
file://0169-Add-efi-export-env-and-efi-load-env-commands.patch \
file://0170-Make-it-possible-to-subtract-conditions-from-debug.patch \
file://0171-Export-all-variables-from-the-initial-context-when-c.patch \
file://0172-Fix-the-looking-up-grub.cfg-XXX-while-tftp-booting.patch \
file://0173-Try-to-set-fPIE-and-friends-on-libgnu.a.patch \
file://0174-Don-t-make-grub_strtoull-print-an-error-if-no-conver.patch \
file://0175-Fix-the-type-of-grub_efi_status_t.patch \
file://0176-mkimage-Use-EFI32_HEADER_SIZE-define-in-arm-efi-case.patch \
file://0177-mkimage-Align-efi-sections-on-4k-boundary.patch \
file://0178-mkimage-Clarify-file-alignment-in-efi-case.patch \
file://0179-grub.d-Split-out-boot-success-reset-from-menu-auto-h.patch \
file://0180-Fix-systemctl-kexec-exit-status-check.patch \
file://0181-Print-grub-emu-linux-loader-messages-as-debug.patch \
file://0182-Don-t-assume-that-boot-commands-will-only-return-on-.patch \
file://0183-Fix-undefined-references-for-fdt-when-building-with-.patch \
file://0184-arm-Move-trampolines-into-code-section.patch \
file://0185-arm-Align-section-alignment-with-manual-relocation-o.patch \
file://0186-grub-core-loader-efi-fdt.c-Do-not-copy-random-memory.patch \
file://0187-linux-efi-arm-fdt-break-FDT-extra-allocation-space-o.patch \
file://gitignore \
file://strtoull_test.c \
\
file://0001-grub-rpm-sort-make-rpm-sort-actually-optional.patch \
"
SRC_URI[md5sum] = "8a4a2a95aac551fb0fba860ceabfa1d3"
SRC_URI[sha256sum] = "810b3798d316394f94096ec2797909dbf23c858e48f7b3830826b8daa06b7b0f"

do_gitrepo() {
        cp ${WORKDIR}/gitignore ${S}/.gitignore
	cp ${WORKDIR}/strtoull_test.c ${S}/grub-core/tests/strtoull_test.c
        cd ${S}
        git init
	echo '![[:digit:]][[:digit:]]_*.in' > util/grub.d/.gitignore
	echo '!*.[[:digit:]]' > util/.gitignore
	echo '!config.h' > include/grub/emu/.gitignore
	rm -f configure
        git add .
        git commit -a -q -m "grub-${PV} baseline."
}
do_unpack[postfuncs] = "do_gitrepo"

DEPENDS = "flex-native bison-native"

COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*)-(linux.*|freebsd.*)'
COMPATIBLE_HOST_armv7a = 'null'
COMPATIBLE_HOST_armv7ve = 'null'

# configure.ac has code to set this automagically from the target tuple
# but the OE freeform one (core2-foo-bar-linux) don't work with that.

GRUBPLATFORM_arm = "efi"
GRUBPLATFORM_aarch64 = "efi"
GRUBPLATFORM ??= "pc"

inherit autotools gettext texinfo pkgconfig

EXTRA_OECONF = "--with-platform=${GRUBPLATFORM} \
                --disable-grub-mkfont \
                --program-prefix="" \
                --enable-liblzma=no \
                --enable-libzfs=no \
		--enable-rpm-sort=no \
                --enable-largefile \
                --disable-werror \
                --with-utils=host \
		"HOST_CFLAGS=${BUILD_CFLAGS}" \
		"HOST_CPPFLAGS=-I{B}" \
		"HOST_LDFLAGS=${BUILD_LDFLAGS}" \
		"TARGET_CFLAGS=${CFLAGS}" \
		"TARGET_CPPFLAGS=-I{B}" \
		"TARGET_LDFLAGS=-I{LDFLAGS}" \
"

PACKAGECONFIG ??= ""
PACKAGECONFIG[grub-mount] = "--enable-grub-mount,--disable-grub-mount,fuse"
PACKAGECONFIG[device-mapper] = "--enable-device-mapper,--disable-device-mapper,libdevmapper"

do_configure_prepend() {
	( cd ${S}
	${S}/autogen.sh )
}

RDEPENDS_${PN}_class-native = ""

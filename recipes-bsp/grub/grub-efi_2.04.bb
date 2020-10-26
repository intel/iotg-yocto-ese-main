require grub204.inc conf/image-uefi.conf

GRUBPLATFORM = "efi"

DEPENDS_append = " grub-native"
RDEPENDS_${PN} = "diffutils freetype grub-common virtual/grub-bootconf"

SRC_URI_append = " file://cfg"

# Determine the target arch for the grub modules
python __anonymous () {
    import re
    target = d.getVar('TARGET_ARCH')
    prefix = "" if d.getVar('EFI_PROVIDER') == "grub-efi" else "grub-efi-"
    if target == "x86_64":
        grubtarget = 'x86_64'
        grubimage = prefix + "bootx64.efi"
    elif re.match('i.86', target):
        grubtarget = 'i386'
        grubimage = prefix + "bootia32.efi"
    elif re.match('aarch64', target):
        grubtarget = 'arm64'
        grubimage = prefix + "bootaa64.efi"
    elif re.match('arm', target):
        grubtarget = 'arm'
        grubimage = prefix + "bootarm.efi"
    else:
        raise bb.parse.SkipRecipe("grub-efi is incompatible with target %s" % target)
    d.setVar("GRUB_TARGET", grubtarget)
    d.setVar("GRUB_IMAGE", grubimage)
    prefix = "grub-efi-" if prefix == "" else ""
    d.setVar("GRUB_IMAGE_PREFIX", prefix)
}

inherit deploy

CACHED_CONFIGUREVARS += "ac_cv_path_HELP2MAN="
EXTRA_OECONF += "--enable-efiemu=no"

# ldm.c:114:7: error: trampoline generated for nested function 'hook' [-Werror=trampolines]
# and many other places in the grub code when compiled with some native gcc compilers (specifically, gentoo)
CFLAGS_append_class-native = " -Wno-error=trampolines"

do_mkimage() {
	cd ${B}
	# Search for the grub.cfg on the local boot media by using the
	# built in cfg file provided via this recipe
	grub-mkimage -c ../cfg -p ${EFIDIR} -d ./grub-core/ \
	               -O ${GRUB_TARGET}-efi -o ./${GRUB_IMAGE_PREFIX}${GRUB_IMAGE} \
	               ${GRUB_BUILDIN}
}

addtask mkimage before do_install after do_compile

do_install_append() {
	install -d ${D}${EFI_FILES_PATH}
	install -m 644 ${B}/${GRUB_IMAGE_PREFIX}${GRUB_IMAGE} ${D}${EFI_FILES_PATH}/${GRUB_IMAGE}
}

# make race condition fix
do_compile_prepend() {
	oe_runmake grub_script.tab.h
	oe_runmake grub_script.tab.c
	oe_runmake grub_script.yy.h
	oe_runmake grub_script.yy.c
}

do_install() {
    oe_runmake 'DESTDIR=${D}' -C grub-core install
}

GRUB_BUILDIN ?= "boot linux ext2 fat serial part_msdos part_gpt normal \
                 efi_gop iso9660 configfile search loadenv test"

do_deploy() {
	install -m 644 ${B}/${GRUB_IMAGE_PREFIX}${GRUB_IMAGE} ${DEPLOYDIR}
}

addtask deploy after do_install before do_build

FILES_${PN} = "${libdir}/grub/${GRUB_TARGET}-efi \
               ${datadir}/grub \
               "

PACKAGES += "${PN}-bootimg"
FILES_${PN}-bootimg = "${EFI_FILES_PATH}/${GRUB_IMAGE}"
ALLOW_EMPTY_${PN}-bootimg = "1"

# 64-bit binaries are expected for the bootloader with an x32 userland
INSANE_SKIP_${PN}_append_linux-gnux32 = " arch"
INSANE_SKIP_${PN}-dbg_append_linux-gnux32 = " arch"
INSANE_SKIP_${PN}_append_linux-muslx32 = " arch"
INSANE_SKIP_${PN}-dbg_append_linux-muslx32 = " arch"

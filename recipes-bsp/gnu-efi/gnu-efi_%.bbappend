
inherit gnu-efi
GNU_EFI_FIXUP = "${GNU_EFI_ARCH}"
GNU_EFI_FIXUP:x86-64 = "x86_64"

do_install:append:x86-64() {
	ln -s "${GNU_EFI_FIXUP}" "${D}${includedir}/efi/${GNU_EFI_ARCH}"
}

do_install:append(){
	install -m 0755 -d ${D}${libdir}/gnuefi/${GNU_EFI_ARCH}
	ln -sv ../../crt0-efi-${GNU_EFI_FIXUP}.o ${D}${libdir}/gnuefi/${GNU_EFI_ARCH}/crt0.o
	ln -sv ../../elf_${GNU_EFI_FIXUP}_efi.lds ${D}${libdir}/gnuefi/${GNU_EFI_ARCH}/efi.lds
	ln -sv ../../crt0-efi-${GNU_EFI_FIXUP}.o ${D}${libdir}/gnuefi/${GNU_EFI_ARCH}/
	ln -sv ../../elf_${GNU_EFI_FIXUP}_efi.lds ${D}${libdir}/gnuefi/${GNU_EFI_ARCH}/
	ln -sv ../../libefi.a ${D}${libdir}/gnuefi/${GNU_EFI_ARCH}/
	ln -sv ../../libgnuefi.a ${D}${libdir}/gnuefi/${GNU_EFI_ARCH}/
}

FILES:${PN}:append = " ${libdir}/gnuefi/${GNU_EFI_ARCH}/*.lds"
FILES:${PN}-dev:append = " ${libdir}/gnuefi/${GNU_EFI_ARCH}/*.o"
FILES:${PN}-staticdev:append = " ${libdir}/gnuefi/${GNU_EFI_ARCH}/*.a"

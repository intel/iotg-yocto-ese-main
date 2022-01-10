SUMMARY = "Helper startup script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SRC_URI = "file://installer-init.sh file://S00sysinit.sh file://inittab file://ese-installer"
RDEPENDS_${PN} += "busybox eudev screen gptfdisk base-passwd rsync findutils grep dosfstools e2fsprogs"
RDEPENDS_${PN} += "e2fsprogs-badblocks e2fsprogs-dumpe2fs e2fsprogs-e2fsck e2fsprogs-e2scrub e2fsprogs-mke2fs e2fsprogs-resize2fs e2fsprogs-tune2fs"
RDEPENDS_${PN} += "util-linux util-linux-blockdev util-linux util-linux-findfs util-linux-blkid util-linux-blkdiscard util-linux-lsblk util-linux-sfdisk"
RDEPENDS_${PN} += "efibootmgr efivar"

S = "${WORKDIR}"

do_install() {
	install -m 755 -d ${D}/dev
	install -m 755 -d ${D}${sysconfdir}
	install -m 755 -d ${D}${sysconfdir}/rcS.d
	install -m 755 -d ${D}${bindir}
        install -m 755 ${WORKDIR}/installer-init.sh ${D}/init
	install -m 644 ${WORKDIR}/inittab ${D}${sysconfdir}/inittab
	install -m 644 ${WORKDIR}/S00sysinit.sh ${D}${sysconfdir}/rcS.d/S00sysinit.sh
	install -m 755 ${WORKDIR}/ese-installer ${D}${bindir}/ese-installer
        mknod -m 622 ${D}/dev/console c 5 1
}

inherit allarch

FILES_${PN} += "/init /dev/console ${sysconfdir} ${bindir}"

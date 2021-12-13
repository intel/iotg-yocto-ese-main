DESCRIPTION = "Relabels root partitions after mender updates"
SRC_URI = "file://ArtifactInstall_Leave_00_relabel_ext4"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
RDEPENDS:${PN} = "gawk grep e2fsprogs-tune2fs"

inherit allarch

do_install(){
	install -d ${D}/etc/mender/scripts
	install -m 750 ${WORKDIR}/ArtifactInstall_Leave_00_relabel_ext4 ${D}/etc/mender/scripts
}

FILES:${PN} = "/etc/mender/scripts"

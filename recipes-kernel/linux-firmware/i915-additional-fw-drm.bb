# in addition to 20190815
SRC_URI = "git://anongit.freedesktop.org/drm/drm-firmware;nobranch=1"
SRCREV = "b115fab46c782d47783a7ecb5fc4b182129caebf"
PV_append = "git+${SRCPV}"

LICENSE = "Firmware-i915"
NO_GENERIC_LICENSE[Firmware-i915] = "LICENSE.i915"
LIC_FILES_CHKSUM = "file://LICENSE.i915;md5=2b0b2e0d20984affd4490ba2cba02570"

inherit allarch
S = "${WORKDIR}/git"
CLEANBROKEN = "1"
i915FWDIR = "${D}${nonarch_base_libdir}/firmware/i915"
do_compile(){
	:
}

do_install(){
	install -m 755 -d ${i915FWDIR}
	install -m 644 "${S}/i915/ehl_guc_33.0.4.bin" "${i915FWDIR}"
	install -m 644 "${S}/i915/ehl_huc_ver9_0_0.bin" "${i915FWDIR}"

}

FILES_${PN} = "${nonarch_base_libdir}/firmware/i915"

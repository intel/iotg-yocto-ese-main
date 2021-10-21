#override SRC_URI and SRCREV
SRC_URI = "git://gitlab.freedesktop.org/mesa/mesa.git;protocol=https;branch=main"
SRCREV = "0e0633ca49425dbc869521cede6a82d2d91c8042"
LIC_FILES_CHKSUM = "file://docs/license.rst;md5=17a4ea65de7a9ab42437f3131e616a7f"
PV = "21.1.0+"
S = "${WORKDIR}/git"

### prefer iris drivers
GALLIUMDRIVERS_append_x86 = ",iris,kmsro"
GALLIUMDRIVERS_append_x86-64 = ",iris,kmsro"

SRC_URI_append = " file://0001-Allow-enable-DRI-without-DRI-drivers.patch \  
                   file://0001-intel-dev-Add-display_ver-and-set-adl-p-to-13.patch \
                   file://0002-iris-Disable-I915_FORMAT_MOD_Y_TILED_GEN12-on-adl-p-.patch \
                 "

DRIDRIVERS_class-native_remove = " swrast"
DRIDRIVERS_class-nativesdk_remove = " swrast"
GALLIUMDRIVERS_remove = " swrast"

# mesa driver override settings
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILES_mesa-megadriver_append = " ${sysconfdir}"
SRC_URI_append_x86-64_class-target = " \
                      file://mesa_driver.sh \
"
RDEPENDS_${PN}-megadriver_append_class-target_x86-64 = " dmidecode"

# mesa driver settings (should be in mesa-megadriver)
do_install_append_class-target_x86-64() {
        install -m 755 -d ${D}${sysconfdir}/profile.d
        install -Dm0644 ${WORKDIR}/mesa_driver.sh ${D}${sysconfdir}/profile.d/mesa_driver.sh
}
###

#fix comma issue
python(){
    dri = d.getVar('DRIDRIVERS')
    d.setVar('DRIDRIVERS', dri.strip(','))

    vulk = d.getVar('VULKAN_DRIVERS')
    d.setVar('VULKAN_DRIVERS', vulk.strip(','))
}

# fix builds after November 23 2020
PLATFORMS_remove = "drm surfaceless"

# fix configure warnings/errors
PACKAGECONFIG[osmesa] = "-Dosmesa=true,-Dosmesa=false"
PACKAGECONFIG_remove = "elf-tls"
PACKAGECONFIG[elf-tls] = ",,,"
EXTRA_OEMESON_remove = "-Dasm=false"
PACKAGECONFIG_append_class-native=" dri3"
PACKAGECONFIG_append_class-nativesdk=" dri3"

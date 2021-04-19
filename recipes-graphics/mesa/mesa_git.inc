### prefer iris drivers
GALLIUMDRIVERS_append_x86 = ",iris"
GALLIUMDRIVERS_append_x86-64 = ",iris"

SRC_URI_append = " file://0001-Allow-enable-DRI-without-DRI-drivers.patch \  
                   file://0001-Workaround_push_constant_corruption_on_context_switch.patch"

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

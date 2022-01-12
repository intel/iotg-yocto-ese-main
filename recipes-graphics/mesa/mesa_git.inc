#override SRC_URI and SRCREV
SRC_URI = "git://gitlab.freedesktop.org/mesa/mesa.git;protocol=https;branch=main"
SRCREV = "0e0633ca49425dbc869521cede6a82d2d91c8042"
LIC_FILES_CHKSUM = "file://docs/license.rst;md5=17a4ea65de7a9ab42437f3131e616a7f"
PV = "21.1.0+"
S = "${WORKDIR}/git"

### prefer iris drivers
GALLIUMDRIVERS:append:x86 = ",iris,kmsro"
GALLIUMDRIVERS:append:x86-64 = ",iris,kmsro"

SRC_URI:append = " file://0001-Allow-enable-DRI-without-DRI-drivers.patch \  
                   file://0001-intel-dev-Add-display_ver-and-set-adl-p-to-13.patch \
                   file://0002-iris-Disable-I915_FORMAT_MOD_Y_TILED_GEN12-on-adl-p-.patch \
                   file://0001-Add-support-for-DRM_FORMAT_ABGR161616.patch \
                 "

DRIDRIVERS:class-native:remove = " swrast"
DRIDRIVERS:class-nativesdk:remove = " swrast"
GALLIUMDRIVERS:remove = " swrast"

# mesa driver override settings
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
FILES:mesa-megadriver:append = " ${sysconfdir}"
SRC_URI:append:x86-64:class-target = " \
                      file://mesa_driver.sh \
"
RDEPENDS:${PN}-megadriver:append:class-target:x86-64 = " dmidecode"

# mesa driver settings (should be in mesa-megadriver)
do_install:append:class-target:x86-64() {
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
PLATFORMS:remove = "drm surfaceless"

# fix configure warnings/errors
PACKAGECONFIG[osmesa] = "-Dosmesa=true,-Dosmesa=false"
PACKAGECONFIG:remove = "elf-tls"
PACKAGECONFIG[elf-tls] = ",,,"
EXTRA_OEMESON:remove = "-Dasm=false"
PACKAGECONFIG:append:class-native=" dri3"
PACKAGECONFIG:append:class-nativesdk=" dri3"

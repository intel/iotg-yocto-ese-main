### prefer iris drivers
GALLIUMDRIVERS:append:x86 = ",iris,kmsro"
GALLIUMDRIVERS:append:x86-64 = ",iris,kmsro"

SRC_URI:append = " file://0001-egl-gallium-dri-Adding-DRM_FORMAT_ABGR16161616-suppo.patch \
                   file://0002-iris-Add-renderonly-support.patch \
                   file://0003-kmsro-Add-iris-renderonly-support.patch \
                   file://0004-iris-kmsro-use-ro-device-to-allocate-scanout-for-ren.patch \
                   file://0005-Revert-iris-Return-non-zero-stride-for-clear-color-p.patch \
                 "
DRIDRIVERS ?= ""
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

# fix builds after November 23 2020
PLATFORMS:remove = "drm surfaceless"

# fix configure warnings/errors
PACKAGECONFIG[osmesa] = "-Dosmesa=true,-Dosmesa=false"
PACKAGECONFIG:remove = "elf-tls"
PACKAGECONFIG[elf-tls] = ",,,"
EXTRA_OEMESON:remove = "-Dasm=false"
PACKAGECONFIG:append:class-native=" dri3"
PACKAGECONFIG:append:class-nativesdk=" dri3"

require ${COREBASE}/meta/recipes-graphics/mesa/mesa.inc
inherit manpages update-alternatives

LIC_FILES_CHKSUM = "file://docs/license.rst;md5=9aa1bc48c9826ad9fdb16661f6930496"
mesa_url ?= "git://gitlab.freedesktop.org/mesa/mesa;branch=master;protocol=https"
## Upstream free-destkop mesa master Mon Jun 10 14:23:34 2019 -0700
mesa_srcrev ?= "a55dc276a38fa8d146386079459cc85c8a7647ae"
mesa_pv ?= "20.1.0+git${SRCPV}"

SRC_URI = "${mesa_url}"
PV = "${mesa_pv}"
SRCREV = "${mesa_srcrev}"

S = "${WORKDIR}/git"

SRC_URI_append_x86-64_class-target = " \
                      file://mesa_driver.sh \
"
RDEPENDS_${PN}-megadriver_append_class-target_x86-64 = " dmidecode"

# This mesa 18.3.0 related fix should be added in the yocto default recipe meta layer.
# Remove following once the upstream fix is available in the future
FILES_${PN} += "${datadir}/drirc.d/00-mesa-defaults.conf"

#because we cannot rely on the fact that all apps will use pkgconfig,
#make eglplatform.h independent of MESA_EGL_NO_X11_HEADER
do_install_append() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'egl', 'true', 'false', d)}; then
        sed -i -e 's/^#if defined(MESA_EGL_NO_X11_HEADERS)$/#if defined(MESA_EGL_NO_X11_HEADERS) || ${@bb.utils.contains('PACKAGECONFIG', 'x11', '0', '1', d)}/' ${D}${includedir}/EGL/eglplatform.h
    fi
}

# mesa driver settings (should be in mesa-megadriver)
do_install_append_class-target_x86-64() {
	install -m 755 -d ${D}${sysconfdir}/profile.d
	install -Dm0644 ${WORKDIR}/mesa_driver.sh ${D}${sysconfdir}/profile.d/mesa_driver.sh
}

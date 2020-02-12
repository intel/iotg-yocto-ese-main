## Use the temporary ehl mesa_wa.inc for meson build until upstream
## poky mesa recipes get meson build enabled
## require ${COREBASE}/meta/recipes-graphics/mesa/mesa.inc

## This "mesa_wa.inc" is the current ehl bkc poky "mesa.inc" integrated
## with poky staging mesa meson build patch:
## https://patchwork.openembedded.org/patch/159016/
require ./mesa_wa.inc

inherit manpages update-alternatives

mesa_url ?= "git://gitlab.freedesktop.org/mesa/mesa;branch=master;protocol=https"
## Upstream free-destkop mesa master Mon Jun 10 14:23:34 2019 -0700
mesa_srcrev ?= "d8a3501f1b2ef2d66091cc1e9c4ede3fb1b0da10"
mesa_pv ?= "19.1.0+git${SRCPV}"

SRC_URI = "${mesa_url}"
PV = "${mesa_pv}"
SRCREV = "${mesa_srcrev}"

S = "${WORKDIR}/git"

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
do_install_append() {
	install -m 755 -d ${D}${sysconfdir}/profile.d
	if [ -n "${MESA_FORCE_DRIVER}" ]; then
		echo 'export MESA_LOADER_DRIVER_OVERRIDE=${MESA_FORCE_DRIVER}' > ${D}${sysconfdir}/profile.d/mesa_driver.sh
	else
		: > ${D}${sysconfdir}/profile.d/mesa_driver.sh
	fi
}

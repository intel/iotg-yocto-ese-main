SUMMARY = "VA-API support to GStreamer"
HOMEPAGE = "https://gstreamer.freedesktop.org/"
DESCRIPTION = "gstreamer-vaapi consists of a collection of VA-API \
based plugins for GStreamer and helper libraries: `vaapidecode', \
`vaapiconvert', and `vaapisink'."

REALPN = "gstreamer-vaapi"

LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "https://gstreamer.freedesktop.org/src/${REALPN}/${REALPN}-${PV}.tar.xz \
           file://0001-gstreamer-vaapi-drop-use-of-GSlice-allocator.patch \
           file://0002-vaapidec-Fix-uninitialized-vars.patch \
           file://0003-vaapi-Fix-some-code-defects.patch \
           file://0004-gstreamer-vaapi-re-indent-with-GNU-indent-2.2.12.patch \
           file://0005-meson-Fix-libdrm-and-vaapi-configure-checks.patch \
           file://0006-vaapiav1dec-fix-type-conversion-warning.patch \
           file://0007-vaapi-delete-the-unused-dma-caps-variable-when-ensur.patch \
           file://0008-va-vaapi-promote-va-ranks-and-demote-vaapi-ones.patch \
          "

SRC_URI[sha256sum] = "d9ba2fc26bef98c78e982c599f585d46bbb65fe122da89c2d7ab41f468a52c7b"

S = "${WORKDIR}/${REALPN}-${PV}"
DEPENDS = "libva gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"

inherit meson pkgconfig features_check upstream-version-is-even

REQUIRED_DISTRO_FEATURES ?= "opengl"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Dexamples=disabled \
    -Dtests=enabled \
"

PACKAGES =+ "${PN}-tests"

# OpenGL packageconfig factored out to make it easy for distros
# and BSP layers to pick either glx, egl, or no GL. By default,
# try detecting X11 first, and if found (with OpenGL), use GLX,
# otherwise try to check if EGL can be used.
PACKAGECONFIG_GL ?= "${@bb.utils.contains('DISTRO_FEATURES', 'x11 opengl', 'glx', \
                        bb.utils.contains('DISTRO_FEATURES',     'opengl', 'egl', \
                                                                       '', d), d)}"

PACKAGECONFIG ??= "drm encoders \
                   ${PACKAGECONFIG_GL} \
                   ${@bb.utils.filter('DISTRO_FEATURES', 'wayland x11', d)}"

PACKAGECONFIG[drm] = "-Ddrm=enabled,-Ddrm=disabled,udev libdrm"
PACKAGECONFIG[egl] = "-Degl=enabled,-Degl=disabled,virtual/egl"
PACKAGECONFIG[encoders] = "-Dencoders=enabled,-Dencoders=disabled"
PACKAGECONFIG[glx] = "-Dglx=enabled,-Dglx=disabled,virtual/libgl"
PACKAGECONFIG[wayland] = "-Dwayland=enabled,-Dwayland=disabled,wayland-native wayland wayland-protocols"
PACKAGECONFIG[x11] = "-Dx11=enabled,-Dx11=disabled,virtual/libx11 libxrandr libxrender"

FILES:${PN} += "${libdir}/gstreamer-*/*.so"
FILES:${PN}-dbg += "${libdir}/gstreamer-*/.debug"
FILES:${PN}-dev += "${libdir}/gstreamer-*/*.a"
FILES:${PN}-tests = "${bindir}/*"

SUMMARY = "Weston, a Wayland compositor"
DESCRIPTION = "Weston is the reference implementation of a Wayland compositor"
HOMEPAGE = "http://wayland.freedesktop.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=d79ee9e66bb0f95d3386a7acae780b70 \
                    file://libweston/compositor.c;endline=27;md5=6c53bbbd99273f4f7c4affa855c33c0a"

SRC_URI = "https://wayland.freedesktop.org/releases/${BPN}-${PV}.tar.xz \
           file://weston.png \
           file://weston.desktop \
           file://0001-make-error-portable.patch \
           file://xwayland.weston-start \
           file://0001-weston-launch-Provide-a-default-version-that-doesn-t.patch \
           file://hdr/0001-Rename-timeline-object.h-to-libweston-timeline-objec.patch \
           file://hdr/0002-Rename-compositor.h-to-libweston-libweston.h.patch \
           file://hdr/0003-Rename-public-backend-headers.patch \
           file://hdr/0004-Rename-plugin-registry.h-to-libweston-plugin-registr.patch \
           file://hdr/0005-Rename-windowed-output-api.h-to-libweston-windowed-o.patch \
           file://hdr/0006-Rename-matrix.h-to-libweston-matrix.h.patch \
           file://hdr/0007-Rename-config-parser.h-to-libweston-config-parser.h.patch \
           file://hdr/0008-Rename-zalloc.h-to-libweston-zalloc.h.patch \
           file://hdr/0009-Rename-xwayland-api.h-to-libweston-xwayland-api.h.patch \
           file://hdr/0010-Rename-version.h-to-libweston-version.h.patch \
           file://hdr/0011-xwayland-do-not-include-weston.h.patch \
           file://hdr/0012-Move-libweston-desktop.h.patch \
           file://hdr/0013-build-turn-vertex-clipping.c-into-a-dependency.patch \
           file://hdr/0014-libweston-export-weston_linux_sync_file_read_timesta.patch \
           file://hdr/0015-gl-renderer-does-not-need-matrix.c.patch \
           file://hdr/0016-libweston-move-gl-renderer-into-a-subdir.patch \
           file://hdr/0017-gl-renderer-Requirement-based-shader-generation.patch \
           file://hdr/0018-gl-renderer-use-intermediate-texture-for-linear-ligh.patch \
           file://hdr/0019-gl-shaders-Add-debug-scope-to-print-generated-shader.patch \
           file://hdr/0020-Add-color-space-definitions.patch \
           file://hdr/0021-Implement-the-hdr-metadata-unstable-v1-protocol.patch \
           file://hdr/0022-Implement-the-colorspace-unstable-v1-protocol.patch \
           file://hdr/0023-clients-Add-simple-hdr-video-client.patch \
           file://hdr/0024-clients-Add-simple-hdr-video-gbm-client.patch \
           file://hdr/0025-matrix-Add-weston_matrix_diag.patch \
           file://hdr/0026-Colorspace-conversion-matrix-generator.patch \
           file://hdr/0027-compositor.h-Add-new-renderer-interfaces-for-HDR.patch \
           file://hdr/0028-noop-renderer-Implement-the-output-colorspace-HDR-in.patch \
           file://hdr/0029-pixman-renderer-Implement-the-output-colorspace-HDR-.patch \
           file://hdr/0030-gl-renderer-Implement-the-renderer-colorspace-HDR-in.patch \
           file://hdr/0031-gl-renderer-Use-full-damage-when-HDR-state-changes.patch \
           file://hdr/0032-gl-shaders-Add-EOTF-OETF-and-CSC-shaders.patch \
           file://hdr/0033-gl-renderer-Generate-CSC-shaders-based-on-requiremen.patch \
           file://hdr/0034-gl-shaders-Add-tone-mapping-shaders.patch \
           file://hdr/0035-gl-renderer-Generate-HDR-shaders-based-on-requiremen.patch \
           file://hdr/0036-pixel-formats-Add-P010-in-pixel-formats.patch \
           file://hdr/0037-compositor-drm-Parse-HDR-metadata-from-EDID.patch \
           file://hdr/0038-compositor-drm-Add-connector-s-HDR-color-property.patch \
           file://hdr/0039-compositor-drm-Add-connector-s-output-colorspace-pro.patch \
           file://hdr/0040-compositor-drm-Prepare-connector-s-color-state.patch \
           file://hdr/0041-compositor-drm-Reset-HDR-state.patch \
           file://hdr/0042-compositor-drm-Set-HDR-metadata-for-renderer.patch \
           file://hdr/0043-compositor-drm-Allow-HDR-surfaces-in-render-only-mod.patch \
           file://hdr/0044-compositor-drm-Reject-heads-if-HDR-head-exists.patch \
           file://hdr/0045-Fix-compilation-error-associated-with-libweston-depe.patch \
           file://hdr/0046-build-Ensure-the-HDR-patches-build-with-autotools-as.patch \
           file://hdr/0047-Fix-KW-issues-associated-with-the-HDR-patches.patch \
"
SRC_URI[md5sum] = "7c634e262f8a464a076c97fd50ad36b3"
SRC_URI[sha256sum] = "546323a90607b3bd7f48809ea9d76e64cd09718102f2deca6d95aa59a882e612"

UPSTREAM_CHECK_URI = "https://wayland.freedesktop.org/releases.html"

inherit autotools pkgconfig useradd distro_features_check
# depends on virtual/egl
REQUIRED_DISTRO_FEATURES = "opengl"

DEPENDS = "libxkbcommon gdk-pixbuf pixman cairo glib-2.0 jpeg"
DEPENDS += "wayland wayland-protocols libinput virtual/egl pango wayland-native ffmpeg"

WESTON_MAJOR_VERSION = "${@'.'.join(d.getVar('PV').split('.')[0:1])}"

EXTRA_OECONF = "--enable-setuid-install \
                --disable-rdp-compositor \
                --enable-autotools \
                "
PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'kms fbdev wayland egl', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'x11 wayland', 'xwayland', '', d)} \
                   ${@bb.utils.filter('DISTRO_FEATURES', 'pam systemd x11', d)} \
                   clients launch"
#
# Compositor choices
#
# Weston on KMS
PACKAGECONFIG[kms] = "--enable-drm-compositor,--disable-drm-compositor,drm udev virtual/mesa virtual/libgbm mtdev"
# Weston on Wayland (nested Weston)
PACKAGECONFIG[wayland] = "--enable-wayland-compositor,--disable-wayland-compositor,virtual/mesa"
# Weston on X11
PACKAGECONFIG[x11] = "--enable-x11-compositor,--disable-x11-compositor,virtual/libx11 libxcb libxcb libxcursor cairo"
# Headless Weston
PACKAGECONFIG[headless] = "--enable-headless-compositor,--disable-headless-compositor"
# Weston on framebuffer
PACKAGECONFIG[fbdev] = "--enable-fbdev-compositor,--disable-fbdev-compositor,udev mtdev"
# weston-launch
PACKAGECONFIG[launch] = "--enable-weston-launch,--disable-weston-launch,drm"
# VA-API desktop recorder
PACKAGECONFIG[vaapi] = "--enable-vaapi-recorder,--disable-vaapi-recorder,libva"
# Weston with EGL support
PACKAGECONFIG[egl] = "--enable-egl --enable-simple-egl-clients,--disable-egl --disable-simple-egl-clients,virtual/egl"
# Weston with cairo glesv2 support
PACKAGECONFIG[cairo-glesv2] = "--with-cairo-glesv2,--with-cairo=image,cairo"
# Weston with lcms support
PACKAGECONFIG[lcms] = "--enable-lcms,--disable-lcms,lcms"
# Weston with webp support
PACKAGECONFIG[webp] = "--with-webp,--without-webp,libwebp"
# Weston with systemd-login support
PACKAGECONFIG[systemd] = "--enable-systemd-login,--disable-systemd-login,systemd dbus"
# Weston with Xwayland support (requires X11 and Wayland)
PACKAGECONFIG[xwayland] = "--enable-xwayland,--disable-xwayland"
# colord CMS support
PACKAGECONFIG[colord] = "--enable-colord,--disable-colord,colord"
# Clients support
PACKAGECONFIG[clients] = "--enable-clients --enable-simple-clients --enable-demo-clients-install,--disable-clients --disable-simple-clients"
# Weston with PAM support
PACKAGECONFIG[pam] = "--with-pam,--without-pam,libpam"

do_install_append() {
	# Weston doesn't need the .la files to load modules, so wipe them
	rm -f ${D}/${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.la

	# If X11, ship a desktop file to launch it
	if [ "${@bb.utils.filter('DISTRO_FEATURES', 'x11', d)}" ]; then
		install -d ${D}${datadir}/applications
		install ${WORKDIR}/weston.desktop ${D}${datadir}/applications

		install -d ${D}${datadir}/icons/hicolor/48x48/apps
		install ${WORKDIR}/weston.png ${D}${datadir}/icons/hicolor/48x48/apps
	fi

	if [ "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', 'yes', 'no', d)}" = "yes" ]; then
		install -Dm 644 ${WORKDIR}/xwayland.weston-start ${D}${datadir}/weston-start/xwayland
	fi
}

PACKAGES += "${@bb.utils.contains('PACKAGECONFIG', 'xwayland', '${PN}-xwayland', '', d)} \
             libweston-${WESTON_MAJOR_VERSION} ${PN}-examples"

FILES_${PN} = "${bindir}/weston ${bindir}/weston-terminal ${bindir}/weston-info ${bindir}/weston-launch ${bindir}/wcap-decode ${libexecdir} ${libdir}/${BPN}/*.so ${datadir}"

FILES_libweston-${WESTON_MAJOR_VERSION} = "${libdir}/lib*${SOLIBS} ${libdir}/libweston-${WESTON_MAJOR_VERSION}/*.so"
SUMMARY_libweston-${WESTON_MAJOR_VERSION} = "Helper library for implementing 'wayland window managers'."

FILES_${PN}-examples = "${bindir}/*"

FILES_${PN}-xwayland = "${libdir}/libweston-${WESTON_MAJOR_VERSION}/xwayland.so"
RDEPENDS_${PN}-xwayland += "xserver-xorg-xwayland"

RDEPENDS_${PN} += "xkeyboard-config"
RRECOMMENDS_${PN} = "weston-conf liberation-fonts"
RRECOMMENDS_${PN}-dev += "wayland-protocols"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system weston-launch"

SUMMARY = "Framework for defining and tracking users, login sessions, and seats"
HOMEPAGE = "https://consolekit2.github.io/ConsoleKit2/"
BUGTRACKER = "https://github.com/ConsoleKit2/ConsoleKit2/issues"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

DEPENDS = "glib-2.0 glib-2.0-native dbus dbus-glib virtual/libx11"
RDEPENDS_${PN} += "base-files"

inherit autotools pkgconfig distro_features_check gtk-doc gettext
# depends on virtual/libx11
REQUIRED_DISTRO_FEATURES = "x11"

SRC_URI = "git://github.com/ConsoleKit2/ConsoleKit2.git;protocol=https \
           file://0001-pam-ck-connector-fix-srcdir-builddir-build.patch \
          "
SRCREV = "735ae642100c8fef95826186aa2ef749c015cc53"

S = "${WORKDIR}/git"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'pam systemd', d)} drm"

PACKAGECONFIG[pam] = "--enable-pam-module --with-pam-module-dir=${base_libdir}/security,--disable-pam-module,libpam"
PACKAGECONFIG[policykit] = ",,polkit"
PACKAGECONFIG[systemd] = "--with-systemdsystemunitdir=${systemd_unitdir}/system/,--with-systemdsystemunitdir="
PACKAGECONFIG[drm] = "--enable-libdrm,--disable-libdrm,libdrm"

FILES_${PN} += "${exec_prefix}/lib/ConsoleKit \
                ${libdir}/ConsoleKit  ${systemd_unitdir} ${base_libdir} \
                ${datadir}/dbus-1 ${datadir}/PolicyKit ${datadir}/polkit*"

PACKAGES =+ "pam-plugin-ck-connector"
FILES_pam-plugin-ck-connector += "${base_libdir}/security/*.so"
RDEPENDS_pam-plugin-ck-connector += "${PN}"

LEAD_SONAME = "libconsolekit"

RPROVIDES_${PN} = "consolekit"
RREPLACES_${PN} = "consolekit"
RCONFLICTS_${PN} = "consolekit"

do_configure() {
	cd ${S}
	autoreconf --force --install
	# Assume available
	export ac_cv_file__sys_class_tty_tty0_active=yes
	cd ${B}
	autotools_do_configure
}

do_install_append() {
        if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
                install -d ${D}${sysconfdir}/tmpfiles.d
                echo "d ${localstatedir}/log/ConsoleKit - - - -" \
                        > ${D}${sysconfdir}/tmpfiles.d/consolekit.conf
        fi

        # Remove /var/ directories as the daemon creates them as required
        rm -rf ${D}${localstatedir}
}

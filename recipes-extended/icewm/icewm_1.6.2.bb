DESCRIPTION = "Ice Window Manager (IceWM)"
SRC_URI = "git://github.com/ice-wm/icewm.git;protocol=https \
           file://0001-configure.ac-skip-strlcat-strlcpy-check-when-cross-c.patch \
           "
SRCREV = "cddfd1281ce025e6e42631d4144ea3c5dd0f26c6"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4a26952467ef79a7efca4a9cf52d417b"
DEPENDS = "asciidoc-native fontconfig fribidi gdk-pixbuf libice libsm libx11 libxext libxft libxpm libxrandr libxrender libxinerama libxfixes libxdamage libxcomposite"
RDEPENDS_${PN} = "perl"
REQUIRED_DISTRO_FEATURES = "x11"

S = "${WORKDIR}/git"
inherit autotools pkgconfig gettext perlnative distro_features_check

autotools_do_configure(){
	cd "${S}"
	./autogen.sh
	cd -
	oe_runconf
}

EXTRA_OECONF = "--with-libdir=/usr/share/icewm --with-cfgdir=/etc/icewm --with-docdir=/usr/share/doc/${PN}/html \
	--enable-fribidi --enable-xinerama --enable-shape"

FILES_${PN} += "/usr/share/xsessions"

# Note: Requires gdk-pixbuf x11 loader, check gdk-pixbuff recipe

# fix up trying to call just-built binary
inherit qemu
DEPENDS_append = " qemu-native"
do_compile[prefuncs] += "genpref"
genpref_cmdline = "${@qemu_wrapper_cmdline(d, '${STAGING_DIR_TARGET}', ['$PWD/src/.libs','$STAGING_DIR_HOST/${libdir}','$STAGING_DIR_HOST/${base_libdir}'])}"

genpref(){
	cd ${B}
	oe_runmake -C src genpref
	${genpref_cmdline} src/genpref > src/preferences
	cd -
}

ALTERNATIVE_${PN} = "x-session-manager"
ALTERNATIVE_TARGET[x-session-manager] = "${bindir}/icewm-session"
ALTERNATIVE_PRIORITY = "200"
inherit update-alternatives

# needed in 5.19 and later
DEPENDS:append = " libnl"
inherit pkgconfig

# Makefile fails to use pkgconfig
CFLAGS:append = " -I${STAGING_INCDIR}/libnl3"
LDFLAGS:append = " -L${STAGING_LIBDIR} -lnl-3"

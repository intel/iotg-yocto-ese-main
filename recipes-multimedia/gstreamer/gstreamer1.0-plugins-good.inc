require gstreamer1.0-plugins.inc

LICENSE = "GPLv2+ & LGPLv2.1+"

DEPENDS += "gstreamer1.0-plugins-base libcap zlib bzip2"

inherit gettext

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'pulseaudio x11', d)} \
    cairo flac gdk-pixbuf gudev jpeg libpng soup speex taglib v4l2 \
"
# This remove "--exclude=autopoint" option from autoreconf argument to avoid
# configure.ac:30: error: required file './ABOUT-NLS' not found
EXTRA_AUTORECONF = ""

X11DEPENDS = "virtual/libx11 libsm libxrender libxfixes libxdamage"

PACKAGECONFIG[cairo]      = "-Dcairo=enabled,-Dcairo=disabled,cairo"
PACKAGECONFIG[dv1394]     = "-Ddv1394=enabled,-Ddv1394=disabled,libiec61883 libavc1394 libraw1394"
PACKAGECONFIG[flac]       = "-Dflac=enabled,-Dflac=disabled,flac"
PACKAGECONFIG[gdk-pixbuf] = "-Dgdk_pixbuf=enabled,-Dgdk_pixbuf=disabled,gdk-pixbuf"
PACKAGECONFIG[gudev]      = "-Dgudev=enabled,-Dgudev=disabled,libgudev"
PACKAGECONFIG[jack]       = "-Djack=enabled,-Djack=disabled,jack"
PACKAGECONFIG[jpeg]       = "-Djpeg=enabled,-Djpeg=disabled,jpeg"
PACKAGECONFIG[libpng]     = "-Dlibpng=enabled,-Dlibpng=disabled,libpng"
PACKAGECONFIG[libv4l2]    = "-Dlibv4l2=enabled,-Dlibv4l2=disabled,v4l-utils"
PACKAGECONFIG[pulseaudio] = "-Dpulse=enabled,-Dpulse=disabled,pulseaudio"
PACKAGECONFIG[soup]       = "-Dsoup=enabled,-Dsoup=disabled,libsoup-2.4"
PACKAGECONFIG[speex]      = "-Dspeex=enabled,-Dspeex=disabled,speex"
PACKAGECONFIG[taglib]     = "-Dtaglib=enabled,-Dtaglib=disabled,taglib"
PACKAGECONFIG[v4l2]       = "-Dgst_v4l2=enabled -Dv4l2-probe=true,-Dgst_v4l2=disabled"
PACKAGECONFIG[vpx]        = "-Dvpx=enabled,-Dvpx=disabled,libvpx"
PACKAGECONFIG[wavpack]    = "-Dwavpack=enabled,-Dwavpack=disabled,wavpack"
PACKAGECONFIG[x11]        = "-Dx=enabled,-Dx=disabled,${X11DEPENDS}"

EXTRA_OECONF += " \
    --enable-bz2 \
    --enable-oss \
    --enable-zlib \
    --disable-aalib \
    --disable-aalibtest \
    --disable-directsound \
    --disable-libcaca \
    --disable-libdv \
    --disable-oss4 \
    --disable-osx_audio \
    --disable-osx_video \
    --disable-shout2 \
    --disable-sunaudio \
    --disable-waveform \
"

FILES_${PN}-equalizer += "${datadir}/gstreamer-1.0/presets/*.prs"

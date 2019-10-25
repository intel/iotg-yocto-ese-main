require gstreamer1.0.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
"

SRC_URI = " \
        gitsm://github.com/GStreamer/gst-libav.git;protocol=https"

SRCREV = "1e8e040fbc7cfff63f29c5c872685bbb5578d965"

S = "${WORKDIR}/git"

DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base ffmpeg bzip2 xz zlib"

inherit autotools pkgconfig upstream-version-is-even gtk-doc

PACKAGECONFIG ??= "orc yasm"

PACKAGECONFIG[gpl] = "--enable-gpl,--disable-gpl,"
PACKAGECONFIG[libav] = "--with-system-libav,,libav"
PACKAGECONFIG[orc] = "--enable-orc,--disable-orc,orc"
PACKAGECONFIG[yasm] = "--enable-yasm,--disable-yasm,nasm-native"
PACKAGECONFIG[valgrind] = "--enable-valgrind,--disable-valgrind,valgrind"

GSTREAMER_1_0_DEBUG ?= "--disable-debug"

LIBAV_EXTRA_CONFIGURE = "--with-libav-extra-configure"

LIBAV_EXTRA_CONFIGURE_COMMON_ARG = "--target-os=linux \
  --cc='${CC}' --as='${CC}' --ld='${CC}' --nm='${NM}' --ar='${AR}' \
  --ranlib='${RANLIB}' \
  ${GSTREAMER_1_0_DEBUG} \
  --cross-prefix='${HOST_PREFIX}'"

# Disable assembly optimizations for X32, as this libav lacks the support
PACKAGECONFIG_remove_linux-gnux32 = "yasm"
LIBAV_EXTRA_CONFIGURE_COMMON_ARG_append_linux-gnux32 = " --disable-asm"

LIBAV_EXTRA_CONFIGURE_COMMON = \
'${LIBAV_EXTRA_CONFIGURE}="${LIBAV_EXTRA_CONFIGURE_COMMON_ARG}"'

EXTRA_OECONF += "${LIBAV_EXTRA_CONFIGURE_COMMON}"

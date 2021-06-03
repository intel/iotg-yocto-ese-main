require recipes-graphics/xorg-driver/xorg-driver-video.inc
inherit python3-dir

SUMMARY = "X.Org X server -- Xorg video driver for the QXL virtual GPU"

LIC_FILES_CHKSUM = "file://COPYING;md5=97497804c3174a7a98ad7089354ecbd9"

SRCREV = "52c421c650f8813665b31890df691b31fabc366a"
PV = "0.15+git${SRCPV}"
S = "${WORKDIR}/git"

SRC_URI = "git://gitlab.freedesktop.org/xorg/driver/xf86-video-qxl.git;protocol=https"

DEPENDS += "virtual/libx11 drm spice-protocol python3"

DESCRIPTION = "library implementing Secure RTP (RFC 3711)"
HOMEPAGE = "https://github.com/cisco/libsrtp"
SECTION = "libs"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2909fcf6f09ffff8430463d91c08c4e1"

S = "${WORKDIR}/git"
SRCREV = "94ac00d5ac6409e3f6409e4a5edfcdbdaa7fdabe"
SRC_URI = "git://github.com/cisco/libsrtp.git;protocol=https;branch=2_2_x_throttle; \
           "

inherit autotools pkgconfig

EXTRA_OEMAKE += "shared_library"

ALLOW_EMPTY:${PN} = "1"

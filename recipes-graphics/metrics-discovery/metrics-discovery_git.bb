SUMMARY = "Intel(R) Metrics Discovery Application Programming Interface (MDAPI)"
DESCRIPTION = "The MDAPI is a user mode library that provides access to GPU performance data. \
The library has OS abstraction layer that allows for interoperability in different environments."

HOMEPAGE = "https://github.com/intel/metrics-discovery"

BUGTRACKER = "https://github.com/intel/metrics-discovery/issues"

SECTION = "x11"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=8c5c9ac8ffd04a5614befdf63fba6ba8"
PV = "1.5.110+git${SRCPV}"

MDAPI_GIT_URI ?= "git://github.com/intel/metrics-discovery"
UPSTREAM_CHECK_URI = "https://github.com/intel/metrics-discovery/releases"
UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>(\d+(\.\d+)+))"

SRCREV_mdapi ?= "2601e0bffbfaaeec2353a08d18d86568b509425d"

SRC_URI = "${MDAPI_GIT_URI};protocol=https;name=mdapi;destsuffix=git"
SRC_URI_append = " file://0001-CMakeLists-consistently-install-.pc-file-into-correc.patch"

DEPENDS = "libdrm ncurses"

inherit cmake

S = "${WORKDIR}/git"
EXTRA_OECMAKE = "-DMD_PLATFORM=linux -DMD_BUILD_TYPE=release -DMD_LIBDRM_SRC=${STAGING_INCDIR}/libdrm"

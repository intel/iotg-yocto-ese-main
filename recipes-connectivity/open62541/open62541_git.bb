SUMMARY = "Open source implementation of OPC UA aka IEC 62541"
HOMEPAGE = "http://open62541.org"
AUTHOR = "Wong, Vincent Por Yin"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=815ca599c9df247a0c7f619bab123dad"

SRC_URI = "git://github.com/open62541/open62541.git;protocol=https \
           file://0001-fix-PubSub-Enable-dynamic-compilation-of-pubsub-exam.patch \
           file://0009-fix-PubSub-Fix-ETF-XDP-plugin-buffer-overflow.patch \
           file://0002-feature-PubSub-Use-libbpf-for-AF_XDP-receive-update-.patch \
           file://0003-feature-PubSub-add-support-for-AF_XDP-transmission.patch \
           file://0004-fix-PubSub-XDP-dynamic-compilation.patch \
           file://0005-fix-PubSub-update-example-to-set-XDP-queue-flags.patch \
           file://0006-test-PubSub-Configuration-used-for-compile-test.patch \
           file://0007-feature-PubSub-Add-ETF-LaunchTime-support-for-XDP-tr.patch \
           file://0008-fix-PubSub-AF_XDP-RX-release-mechanism-AF_PACKET-com.patch \
           file://0010-fix-PubSub-xdp-socket-cleanup-routine.patch \
           file://0011-fix-PubSub-fix-null-checking-possible-memleak-klocwo.patch \
           file://0012-fix-PubSub-remove-hardcoded-etf-layer-receive-timeou.patch \
          "

SRCREV = "a77b20ff940115266200d31d30d3290d6f2d57bd"

S = "${WORKDIR}/git"
PV = "1.0.1-git${SRCPV}"

DEPENDS += "libbpf"
inherit cmake python3native

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=ON \
                 -DUA_ENABLE_PUBSUB=ON \
                 -DUA_ENABLE_PUBSUB_ETH_UADP=ON \
                 -DUA_BUILD_EXAMPLES=OFF \
                 -DUA_ENABLE_PUBSUB_CUSTOM_PUBLISH_HANDLING=ON \
                 -DUA_ENABLE_PUBSUB_ETH_UADP_ETF=ON \
                 -DUA_ENABLE_PUBSUB_ETH_UADP_XDP=ON \
                 -DCMAKE_INSTALL_PREFIX=${prefix} \
                 -DLIB_INSTALL_DIR=${libdir} "

SUMMARY = "Intel Media SDK samples and binaries"
HOMEPAGE = "https://github.com/Intel-Media-SDK/MediaSDK"
BUGTRACKER = "https://github.com/Intel-Media-SDK/MediaSDK/issues"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3cb331af679cd8f968bf799a9c55b46e"

SRC_URI = "\
	git://github.com/Intel-Media-SDK/MediaSDK.git;protocol=https \
        file://0002-Temporary-removing-sample_rotate-compilation-to-WA-G.patch \
	file://0001-fix-MSDK-weston-flickering-issue.patch \
	file://0001-Fix-ITT-include-directory-path.patch \
	file://0001-enable-10bit-render-support-in-wayland.patch \
        "
SRCREV = "f87a709646f1af1aafcf8e5fe14baf01ddb39678"

DEPENDS += "libdrm libva itt"

S = "${WORKDIR}/git"

inherit pkgconfig perlnative cmake distro_features_check


# Set to "release" or "debug" accordingly
BUILD_MODE ??= "debug"

export MFX_HOME="${S}"


PACKAGECONFIG ?= "opencl"
PACKAGECONFIG_append = " ${@bb.utils.contains("DISTRO_FEATURES", "x11", "dri3", "", d)} \
                         ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "wayland", "", d)}"

PACKAGECONFIG[dri3]     = "-DENABLE_X11_DRI3=ON, -DENABLE_X11_DRI3=OFF"
PACKAGECONFIG[wayland]  = "-DENABLE_WAYLAND=ON, -DENABLE_WAYLAND=OFF, wayland wayland-native"
PACKAGECONFIG[opencl]   = "-DENABLE_OPENCL=ON -DOPENCL_LIBRARY_PATH=${STAGING_LIBDIR} -DOPENCL_INCLUDE=${STAGING_INCDIR}, -DENABLE_OPENCL=OFF, ocl-icd"


EXTRA_OECMAKE += "-DMFX_INCLUDE=${S}/api/include -DBUILD_SAMPLES=ON -DENABLE_ITT=ON"

# compiled code should not be in datadir
do_install_append(){
	mv ${D}${datadir}/mfx/samples ${D}${libdir}/mfx/samples
}

FILES_${PN}-staticdev += "${libdir}/mfx/samples/*.a"
FILES_${PN} += "${libdir}/mfx/*.so ${datadir}/mfx/plugins.cfg ${libdir}/mfx/samples"

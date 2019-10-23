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
#do_install_append(){
#	mv ${D}${datadir}/mfx/samples ${D}${libdir}/mfx/samples
#}

do_install_append () {
     install -d -m 644 ${D}${libdir}
     install -d -m 755 ${D}/opt/
     install -d -m 755 ${D}/opt/intel
     install -d -m 755 ${D}/opt/intel/mediasdk/
     install -d -m 755 ${D}/opt/intel/mediasdk/plugins
     install -d -m 755 ${D}/opt/intel/mediasdk/samples
     install -d -m 755 ${D}/opt/intel/mediasdk/src/
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_encode/include
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_encode/src
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_decode/include
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_decode/src
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_vpp/include
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_vpp/src
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_multi_transcode/include
     install -d -m 755 ${D}/opt/intel/mediasdk/src/sample_multi_transcode/src
     install -d -m 755 ${D}/opt/intel/mediasdk/lib64
     install -d -m 755 ${D}/opt/intel/mediasdk/lib64/lin_x64
     #install -d -m 755 ${D}/opt/intel/mediasdk/doc
     #install -d -m 755 ${D}/opt/intel/mediasdk/doc/samples
     #install -d -m 755 ${D}/opt/intel/mediasdk/doc/api
     install -d -m 755 ${D}/opt/intel/mediasdk/api/include
     install -d -m 755 ${D}/opt/intel/mediasdk/api/opensource     
    
     install -m 755 ${B}/__bin/release/sample_* ${D}/opt/intel/mediasdk/samples/
     install -m 755 ${B}/__lib/release/libvpp_plugin.a ${D}/opt/intel/mediasdk/lib64/
     install -m 644 ${B}/__bin/release/libmfxhw64*.so ${D}/opt/intel/mediasdk/lib64/libmfxhw64-p.so.${PV}
     install -m 644 ${B}/__bin/release/libmfx*.so ${D}/opt/intel/mediasdk/lib64/
     install -m 644 ${B}/__bin/release/libsample_rotate_plugin.so ${D}/opt/intel/mediasdk/samples/
     install -m 644 ${B}/__bin/release/libmfx_wayland.so ${D}/opt/intel/mediasdk/samples/
     install -m 644 ${B}/__bin/release/libmfx_hevce_hw64*.so ${D}/opt/intel/mediasdk/plugins/
     install -m 644 ${B}/__bin/release/libmfx_hevcd_hw64*.so ${D}/opt/intel/mediasdk/plugins/
     install -m 644 ${B}/__bin/release/libmfx_h264la_hw64*.so ${D}/opt/intel/mediasdk/plugins/
     install -m 644 ${B}/__bin/release/libmfx_hevc_fei_hw64*.so ${D}/opt/intel/mediasdk/plugins/
     install -m 644 ${B}/__bin/release/libmfx_vp8d_hw64*.so ${D}/opt/intel/mediasdk/plugins/
     install -m 644 ${B}/__bin/release/libmfx_vp9d_hw64*.so ${D}/opt/intel/mediasdk/plugins/
     install -m 644 ${B}/__bin/release/libmfx_vp9e_hw64*.so ${D}/opt/intel/mediasdk/plugins/
     install -m 644 ${S}/api/include/* ${D}/opt/intel/mediasdk/api/include/
     
     install -m 644 ${S}/samples/sample_encode/include/* ${D}/opt/intel/mediasdk/src/sample_encode/include
     install -m 644 ${S}/samples/sample_encode/src/* ${D}/opt/intel/mediasdk/src/sample_encode/src/
     install -m 644 ${S}/samples/sample_decode/include/* ${D}/opt/intel/mediasdk/src/sample_decode/include
     install -m 644 ${S}/samples/sample_decode/src/* ${D}/opt/intel/mediasdk/src/sample_decode/src/
     install -m 644 ${S}/samples/sample_vpp/include/* ${D}/opt/intel/mediasdk/src/sample_vpp/include
     install -m 644 ${S}/samples/sample_vpp/src/* ${D}/opt/intel/mediasdk/src/sample_vpp/src/
     install -m 644 ${S}/samples/sample_multi_transcode/include/* ${D}/opt/intel/mediasdk/src/sample_multi_transcode/include
     install -m 644 ${S}/samples/sample_multi_transcode/src/* ${D}/opt/intel/mediasdk/src/sample_multi_transcode/src/

     cp ${D}/usr/share/mfx/samples/libvpp_plugin.a ${D}/opt/intel/mediasdk/samples 
     cp ${D}/usr/share/mfx/samples/libmfx_wayland.so ${D}/opt/intel/mediasdk/samples 
}

FILES_${PN} += " \
                 ${libdir}/mfx \
                 ${datadir}/mfx/plugins.cfg \
		 /opt/intel/mediasdk/samples/ \
                 /opt/intel/mediasdk/plugins/ \
                 ${LIBDIR}/libmfxhw64-p.so.${PV} \
                 ${libdir}/libsample_rotate_plugin.so \
                 ${libdir}/libmfx_wayland.so"
FILES_${PN}-staticdev += "${libdir}/mfx/samples/*.a \
			/opt/intel/mediasdk/samples/*.a"
FILES_${PN}-dev = "/opt/intel/mediasdk/src/"

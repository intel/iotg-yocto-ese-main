require gstreamer1.0-plugins-common.inc

SUMMARY = "'Base' GStreamer plugins and helper libraries"
HOMEPAGE = "https://gstreamer.freedesktop.org/"
BUGTRACKER = "https://gitlab.freedesktop.org/gstreamer/gst-plugins-base/-/issues"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=69333daa044cb77e486cc36129f7a770"

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-plugins-base/gst-plugins-base-${PV}.tar.xz \
           file://0001-ENGR00312515-get-caps-from-src-pad-when-query-caps.patch \
           file://0003-viv-fb-Make-sure-config.h-is-included.patch \
           file://0002-ssaparse-enhance-SSA-text-lines-parsing.patch \
           file://0001-oggdemux-drop-use-of-GSlice.patch \
           file://0002-playback-drop-use-of-GSlice.patch \
           file://0003-encodebin-drop-use-of-GSlice.patch \
           file://0004-typefindfunctions-drop-use-of-GSlice.patch \
           file://0005-ximage-xvimage-drop-use-of-GSlice.patch \
           file://0006-tests-rtp-drop-use-of-GSlice.patch \
           file://0007-fdmemory-drop-use-of-GSlice.patch \
           file://0008-libs-audio-drop-use-of-GSlice.patch \
           file://0009-libs-pbutils-drop-use-of-GSlice.patch \
           file://0010-libs-sdp-drop-use-of-GSlice.patch \
           file://0011-tag-drop-use-of-GSlice.patch \
           file://0012-libs-video-drop-use-of-GSlice.patch \
           file://0013-libs-gl-drop-use-of-GSlice.patch \
           file://0014-allocators-Add-a-DRM-Dumb-Allocator.patch \
           file://0015-gst-plugins-base-re-indent-with-GNU-indent-2.2.12.patch \
           file://0016-videorate-Fix-incorrect-drop-value-when-drop_only-is.patch \
           file://0017-pbutils-add-video-x-ivf-to-descriptions.patch \
           file://0018-meson-Add-a-wrap-file-for-libgudev.patch \
           file://0019-video-add-dma-format-and-info-helper-functions.patch \
           file://0020-tests-add-tests-for-GstVideoInfoDmaDrm.patch \
           file://0021-video-video-info-dma-Fix-return-value.patch \
           file://0022-gl-move-gl-base-mixer-to-library.patch \
           file://0023-gl-mixer-make-fbo-instance-field-private-and-provide.patch \
           file://0024-glmixer-remove-reset-vfunc.patch \
           file://0025-glmixer-remove-set_caps-vfunc.patch \
           file://0026-glmixer-don-t-add-rgba-templates-by-default.patch \
           file://0027-gl-update-docs-for-mixer-move.patch \
           file://0028-videodecoder-Refactor-post-decode-PTS-DTS-recovery-c.patch \
           file://0029-videodecoder-refactor-and-document-finish_frame-some.patch \
           file://0030-videodecoder-Remove-unused-internal-fields.patch \
           file://0031-videodecoder-Only-use-subframes-internal-values-in-s.patch \
           file://0032-gl-add-a-method-to-get-DMA-formats-and-modifiers.patch \
           file://0033-gl-replace-_drm_direct_fourcc_from_info.patch \
           file://0034-video-info-dma-add-gst_video_info_dma_drm_from_video.patch \
           file://0035-gl-add-gst_egl_image_check_dmabuf_direct_with_dma_dr.patch \
           file://0036-gl-EGL-image-methods-to-import-dmabufs-with-modifier.patch \
           file://0037-videotestsrc-Move-video-x-bayer-caps-parsing-in-one-.patch \
           file://0038-videotestsrc-Simplify-ARGB-to-Bayer-conversion.patch \
           file://0039-videotestsrc-Support-video-x-bayer-10-12-14-16-bit-d.patch \
           file://0040-gl-Add-a-print-function-to-dump-all-EGL-image-drm-fo.patch \
           file://0041-video-info-dma-add-gst_video_info_dma_drm_to_video_i.patch \
           file://0042-videorate-properly-handle-variable-framerate-input-a.patch \
           file://0043-video-Add-new-GST_VIDEO_FORMAT_DMA_DRM-video-format.patch \
           file://0044-video-dma-Set-DMA-caps-format-field-to-DMA_DRM.patch \
           file://0045-video-add-GST_VIDEO_DMA_DRM_CAPS_MAKE.patch \
           file://0046-test-Update-all-video-related-tests-because-of-addin.patch \
           file://0047-test-video-Fix-the-caps-comparing-typo-for-video-drm.patch \
           file://0048-gl-colorconvert-use-swizzle-indices-instead-of-chars.patch \
           file://0049-gl-format-add-helper-for-returning-the-number-of-com.patch \
           file://0050-gl-expose-calculating-swizzle-indices-to-from-RGBA-Y.patch \
           file://0051-glcolorconvert-expose-the-YUV-RGB-glsl-function.patch \
           file://0052-glcolorconvert-expose-the-swizzle-glsl-functions.patch \
           file://0053-glcolorconvert-Fix-syntax-for-GLSL-shaders.patch \
           file://0054-gl-add-a-method-to-check-whether-DMA-modifier-is-sup.patch \
           file://0055-glupload-add-a-helper-function-to-convert-gst-format.patch \
           file://0056-glupload-add-a-helper-function-to-convert-drm-format.patch \
           file://0057-glupload-Add-a-helper-function-of-_filter_caps_with_.patch \
           file://0058-glupload-enable-drm-kind-caps-in-glupload-plugin-for.patch \
           file://0059-glupload-change-the-accept-function-to-import-dmabuf.patch \
           file://0060-glupload-make-raw-manner-only-consider-system-memory.patch \
           file://0061-glupload-make-meta-upload-manner-only-consider-Textu.patch \
           file://0062-glupload-make-gl-memory-upload-manner-only-consider-.patch \
           file://0063-glupload-make-directviv-upload-manner-only-consider-.patch \
           file://0064-glupload-make-nvmm-upload-manner-only-consider-syste.patch \
           file://0065-glupload-Delete-the-flag-of-METHOD_FLAG_CAN_ACCEPT_R.patch \
           file://0066-doc-Update-the-caps-for-glupload-related-plugins.patch \
           file://0067-video-remove-spurious-gst_caps_make_writable.patch \
           file://0068-video-dma-let-gst_video_info_dma_drm_to_caps-return-.patch \
           file://0069-gleglimage-cache-EGL-images-per-DmabufUpload.patch \
           file://0070-glvideomixer-Implement-force-live-and-min-upstream-l.patch \
           file://0071-gldisplay-Add-gst_gl_display_ensure_context.patch \
           file://0072-glbasesrc-use-gst_display_ensure_context.patch \
           file://0073-glbasemixer-use-gst_display_ensure_context.patch \
           file://0074-glbasefilter-use-gst_display_ensure_context.patch \
           file://0075-glstereosplit-use-gst_display_ensure_context.patch \
           file://0076-glupload-Fix-a-memory-leak-point-in-gst_egl_image_ca.patch \
           file://0077-video-DMA-Add-several-video-formats-support.patch \
           "
SRC_URI[sha256sum] = "292424e82dea170528c42b456f62a89532bcabc0508f192e34672fb86f68e5b8"

S = "${WORKDIR}/gst-plugins-base-${PV}"

DEPENDS += "iso-codes util-linux zlib"

inherit gobject-introspection

# opengl packageconfig factored out to make it easy for distros
# and BSP layers to choose OpenGL APIs/platforms/window systems
PACKAGECONFIG_X11 = "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'opengl glx', '', d)}"
PACKAGECONFIG_GL ?= "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gles2 egl ${PACKAGECONFIG_X11}', '', d)}"

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${PACKAGECONFIG_GL} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'alsa x11', d)} \
    jpeg ogg pango png theora vorbis \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland egl', '', d)} \
"

OPENGL_APIS = 'opengl gles2'
OPENGL_PLATFORMS = 'egl glx'

X11DEPENDS = "virtual/libx11 libsm libxrender libxv"
X11ENABLEOPTS = "-Dx11=enabled -Dxvideo=enabled -Dxshm=enabled"
X11DISABLEOPTS = "-Dx11=disabled -Dxvideo=disabled -Dxshm=disabled"

PACKAGECONFIG[alsa]         = "-Dalsa=enabled,-Dalsa=disabled,alsa-lib"
PACKAGECONFIG[cdparanoia]   = "-Dcdparanoia=enabled,-Dcdparanoia=disabled,cdparanoia"
PACKAGECONFIG[graphene]     = "-Dgl-graphene=enabled,-Dgl-graphene=disabled,graphene"
PACKAGECONFIG[jpeg]         = "-Dgl-jpeg=enabled,-Dgl-jpeg=disabled,jpeg"
PACKAGECONFIG[ogg]          = "-Dogg=enabled,-Dogg=disabled,libogg"
PACKAGECONFIG[opus]         = "-Dopus=enabled,-Dopus=disabled,libopus"
PACKAGECONFIG[pango]        = "-Dpango=enabled,-Dpango=disabled,pango"
PACKAGECONFIG[png]          = "-Dgl-png=enabled,-Dgl-png=disabled,libpng"
# This enables Qt5 QML examples in -base. The Qt5 GStreamer
# qmlglsink and qmlglsrc plugins still exist in -good.
PACKAGECONFIG[qt5]          = "-Dqt5=enabled,-Dqt5=disabled,qtbase qtdeclarative qtbase-native"
PACKAGECONFIG[theora]       = "-Dtheora=enabled,-Dtheora=disabled,libtheora"
PACKAGECONFIG[tremor]       = "-Dtremor=enabled,-Dtremor=disabled,tremor"
PACKAGECONFIG[visual]       = "-Dlibvisual=enabled,-Dlibvisual=disabled,libvisual"
PACKAGECONFIG[vorbis]       = "-Dvorbis=enabled,-Dvorbis=disabled,libvorbis"
PACKAGECONFIG[x11]          = "${X11ENABLEOPTS},${X11DISABLEOPTS},${X11DEPENDS}"

# OpenGL API packageconfigs
PACKAGECONFIG[opengl]       = ",,virtual/libgl libglu"
PACKAGECONFIG[gles2]        = ",,virtual/libgles2"

# OpenGL platform packageconfigs
PACKAGECONFIG[egl]          = ",,virtual/egl"
PACKAGECONFIG[glx]          = ",,virtual/libgl"

# OpenGL window systems (except for X11)
PACKAGECONFIG[gbm]          = ",,virtual/libgbm libgudev libdrm"
PACKAGECONFIG[wayland]      = ",,wayland-native wayland wayland-protocols libdrm"
PACKAGECONFIG[dispmanx]     = ",,virtual/libomxil"
PACKAGECONFIG[viv-fb]       = ",,virtual/libgles2 virtual/libg2d"

OPENGL_WINSYS = "${@bb.utils.filter('PACKAGECONFIG', 'x11 gbm wayland dispmanx egl viv-fb', d)}"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    ${@get_opengl_cmdline_list('gl_api', d.getVar('OPENGL_APIS'), d)} \
    ${@get_opengl_cmdline_list('gl_platform', d.getVar('OPENGL_PLATFORMS'), d)} \
    ${@get_opengl_cmdline_list('gl_winsys', d.getVar('OPENGL_WINSYS'), d)} \
"

FILES:${PN}-dev += "${libdir}/gstreamer-1.0/include/gst/gl/gstglconfig.h"
FILES:${MLPREFIX}libgsttag-1.0 += "${datadir}/gst-plugins-base/1.0/license-translations.dict"

def get_opengl_cmdline_list(switch_name, options, d):
    selected_options = []
    if bb.utils.contains('DISTRO_FEATURES', 'opengl', True, False, d):
        for option in options.split():
            if bb.utils.contains('PACKAGECONFIG', option, True, False, d):
                selected_options += [option]
    if selected_options:
        return '-D' + switch_name + '=' + ','.join(selected_options)
    else:
        return ''

CVE_PRODUCT += "gst-plugins-base"

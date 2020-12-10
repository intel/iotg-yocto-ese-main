FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
    file://weston/0001-gl-renderer-Requirement-based-shader-generation.patch \
    file://weston/0002-gl-renderer-use-intermediate-texture-for-linear-ligh.patch \
    file://weston/0003-gl-shaders-Add-debug-scope-to-print-generated-shader.patch \
    file://weston/0004-Add-color-space-definitions.patch \
    file://weston/0005-Implement-the-hdr-metadata-unstable-v1-protocol.patch \
    file://weston/0006-Implement-the-colorspace-unstable-v1-protocol.patch \
    file://weston/0007-clients-Add-simple-hdr-video-client.patch \
    file://weston/0008-clients-Add-simple-hdr-video-gbm-client.patch \
    file://weston/0009-matrix-Add-weston_matrix_diag.patch \
    file://weston/0010-Colorspace-conversion-matrix-generator.patch \
    file://weston/0011-compositor.h-Add-new-renderer-interfaces-for-HDR.patch \
    file://weston/0012-pixman-renderer-Implement-the-output-colorspace-HDR-.patch \
    file://weston/0013-gl-renderer-Implement-the-renderer-colorspace-HDR-in.patch \
    file://weston/0014-gl-renderer-Use-full-damage-when-HDR-state-changes.patch \
    file://weston/0015-gl-shaders-Add-EOTF-OETF-and-CSC-shaders.patch \
    file://weston/0016-gl-renderer-Generate-CSC-shaders-based-on-requiremen.patch \
    file://weston/0017-gl-shaders-Add-tone-mapping-shaders.patch \
    file://weston/0018-gl-renderer-Generate-HDR-shaders-based-on-requiremen.patch \
    file://weston/0019-pixel-formats-Add-P010-in-pixel-formats.patch \
    file://weston/0020-compositor-drm-Parse-HDR-metadata-from-EDID.patch \
    file://weston/0021-compositor-drm-Add-connector-s-HDR-color-property.patch \
    file://weston/0022-compositor-drm-Add-connector-s-output-colorspace-pro.patch \
    file://weston/0023-compositor-drm-Prepare-connector-s-color-state.patch \
    file://weston/0024-compositor-drm-Set-HDR-metadata-for-renderer.patch \
    file://weston/0025-compositor-drm-Allow-HDR-surfaces-in-render-only-mod.patch \
    file://weston/0026-compositor-drm-Reject-heads-if-HDR-head-exists.patch \
    file://weston/0027-Fix-KW-issues-associated-with-the-HDR-patches.patch \
    file://weston/0001-Fix-KW-issues-associated-with-the-HDR-patches.patch \
"

DEPENDS_append = " ffmpeg"

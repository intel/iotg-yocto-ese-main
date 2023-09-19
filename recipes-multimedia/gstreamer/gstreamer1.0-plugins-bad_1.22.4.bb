require gstreamer1.0-plugins-common.inc
require gstreamer1.0-plugins-license.inc

SUMMARY = "'Bad' GStreamer plugins and helper libraries "
HOMEPAGE = "https://gstreamer.freedesktop.org/"
BUGTRACKER = "https://gitlab.freedesktop.org/gstreamer/gst-plugins-bad/-/issues"

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-plugins-bad/gst-plugins-bad-${PV}.tar.xz \
           file://0001-fix-maybe-uninitialized-warnings-when-compiling-with.patch \
           file://0002-avoid-including-sys-poll.h-directly.patch \
           file://0004-opencv-resolve-missing-opencv-data-dir-in-yocto-buil.patch \
           file://0001-libs-mpegts-drop-use-of-GSlice-allocator.patch \
           file://0002-libs-insertbin-drop-use-of-GSlice-allocator.patch \
           file://0003-libs-codecparsers-drop-use-of-GSlice.patch \
           file://0004-libs-va-drop-use-of-GSlice.patch \
           file://0005-openjpeg-drop-use-of-GSlice.patch \
           file://0006-kate-drop-use-of-GSlice.patch \
           file://0007-resindvd-drop-use-of-GSlice.patch \
           file://0008-srtp-drop-use-of-GSlice.patch \
           file://0009-ttml-drop-use-of-GSlice.patch \
           file://0010-wpe-drop-use-of-GSlice.patch \
           file://0011-x265-drop-use-of-GSlice.patch \
           file://0012-svthevcenc-drop-use-of-GSlice.patch \
           file://0013-dvbsuboverlay-drop-use-of-GSlice.patch \
           file://0014-mxf-drop-use-of-GSlice.patch \
           file://0015-rtmp2-drop-use-of-GSlice.patch \
           file://0016-jp2kdecimator-drop-use-of-GSlice.patch \
           file://0017-mpegtsmux-drop-use-of-GSlice-allocator.patch \
           file://0018-mpegtsdemux-drop-use-of-GSlice.patch \
           file://0019-mpegpsmux-drop-use-of-GSlice.patch \
           file://0020-netsim-drop-use-of-GSlice.patch \
           file://0021-rist-drop-use-of-GSlice.patch \
           file://0022-jpegformat-drop-use-of-GSlice.patch \
           file://0023-msdkallocator-Use-const-ptr-of-videoinfo-in-func-par.patch \
           file://0024-cc708overlay-bump-pango-requirement-and-drop-no-long.patch \
           file://0025-midiparse-drop-use-of-GSlice-allocator.patch \
           file://0026-shm-drop-use-of-GSlice-allocator.patch \
           file://0027-kms-drop-use-of-GSlice-allocator-and-remove-unnecess.patch \
           file://0028-msdkenc-Let-runtime-decide-parameters.patch \
           file://0029-msdkdec-Fix-some-lock-issue.patch \
           file://0030-va-jpegdecoder-Do-not-check-SOS-state-when-parsing-D.patch \
           file://0031-jpegparse-Parse-AVI1-tag-in-app0.patch \
           file://0032-jpegparse-Warn-only-malformed-data-in-APP-data.patch \
           file://0033-msdkdec-Enable-va-caps-at-srcpad.patch \
           file://0034-msdk-Add-a-bufferpool-in-GstMsdkContext-structure.patch \
           file://0035-msdkdec-Add-a-function-to-directly-allocate-output-G.patch \
           file://0036-msdkallocator_libva-Rewrite-gst_msdk_frame_alloc.patch \
           file://0037-msdkdec-Add-a-function-to-create-va-pool.patch \
           file://0038-msdkdec-Apply-the-modified-memory-allocation-logic.patch \
           file://0039-msdkdec-Check-available-surfaces-when-all-pre-alloca.patch \
           file://0040-msdkenc-Set-pts-at-handle_frame.patch \
           file://0041-msdkvpp-correct-the-fixated-caps-for-src-pad.patch \
           file://0042-msdkenc-Fix-scale-ratio-for-frame-duration.patch \
           file://0043-va-Drop-all-GSlice-bits.patch \
           file://0044-va-Add-Win32-backend-GstVaDisplay-implementation.patch \
           file://0045-va-Add-support-for-Win32-backend.patch \
           file://0046-va-Add-Windows-specific-element-type-feature-naming-.patch \
           file://0047-va-Update-device-path-property-description-for-Windo.patch \
           file://0048-kmsallocator-Port-to-the-new-DRM-Dumb-Allocator.patch \
           file://0049-gtkwaylandsink-Remove-redefine-of-GST_CAPS_FEATURE_M.patch \
           file://0050-waylandsink-Stop-modifying-the-display-GstVideoInfo.patch \
           file://0051-gtkwaylandsink-Force-a-redraw-on-resolution-change.patch \
           file://0052-waylandsink-Let-the-baseclass-know-when-frames-are-d.patch \
           file://0053-waylandsink-Refactor-internal-pool-handling.patch \
           file://0054-gtkwaylandsink-Fix-display-wl_window-pool-leaks.patch \
           file://0055-wllinuxdmabuf-Handle-video-meta-inside-the-importer.patch \
           file://0056-wlvideoformat-Fix-sign-issue-for-DRM-fourcc.patch \
           file://0057-wlvideobufferpool-Add-DRM-Dumb-buffer-support.patch \
           file://0058-wayladnsink-Add-DRM-Dumb-allocator-support.patch \
           file://0059-bad-Update-doc-cache-for-waylandsink-changes.patch \
           file://0060-va-Don-t-error-out-on-plugin-registration.patch \
           file://0061-vadisplay_win32-Query-profiles-and-entry-points-on-i.patch \
           file://0062-msdkh264enc-Adding-BGRx-format-DMABuf-support.patch \
           file://0063-gst-plugins-bad-re-indent-with-GNU-indent-2.2.12.patch \
           file://0064-h265parser-Add-an-API-for-HEVCDecoderConfigurationRe.patch \
           file://0065-h265parse-Use-gst_h265_parser_parse_decoder_config_r.patch \
           file://0066-h265decoder-Use-gst_h265_parser_parse_decoder_config.patch \
           file://0067-qsvh265dec-Use-gst_h265_parser_parse_decoder_config_.patch \
           file://0068-h265timestamper-Use-gst_h265_parser_parse_decoder_co.patch \
           file://0069-h264parser-Fix-return-value-parsing-short-header.patch \
           file://0070-test-h265parser-Test-for-NAL-missing-header-bytes.patch \
           file://0071-h264parse-Stop-considering-NO_NAL-as-an-error.patch \
           file://0072-va-Return-default-device-from-device-path-property-g.patch \
           file://0073-va-add-driver-description-in-element-metadata.patch \
           file://0074-msdkvpp-Pass-null-formats-when-create-va-allocator-f.patch \
           file://0075-msdkdec-add-d3d11-caps-for-srcpad.patch \
           file://0076-msdkdec-add-d3d11-pool-during-negotiation.patch \
           file://0077-msdkallocator_d3d-add-alloc-callback-function-for-d3.patch \
           file://0078-msdkdec-add-thiz-ds_has_known_allocator-for-d3d11.patch \
           file://0079-msdkdec-acquire-buffer-from-video-pool-on-Windows.patch \
           file://0080-msdkallocator_d3d-add-gst_msdk_frame_free-callback-f.patch \
           file://0081-msdkdec-expand-retry-times-to-1s.patch \
           file://0082-msdkdec-delete-use_video_memory.patch \
           file://0083-vadecoder-remove-display-reference-in-decode-picture.patch \
           file://0084-vaencoder-remove-display-reference-in-decode-picture.patch \
           file://0085-h264decoder-use-last_output_poc-from-DPB.patch \
           file://0086-gsth254picture-move-internal-API-to-private-header.patch \
           file://0087-msdk-Remove-the-func-gst_msdk_is_msdk_buffer.patch \
           file://0088-msdk-Remove-unused-gstmsdkbufferpool-and-memory.patch \
           file://0089-msdkcontext-Remove-unused-codes-in-msdkcontext.patch \
           file://0090-msdk-Move-all-_gst_caps_has_feature-to-gstmsdkcaps.c.patch \
           file://0091-msdk-Create-msdk-context-without-job_type.patch \
           file://0092-msdk-Add-functions-to-get-data-members-from-MsdkSess.patch \
           file://0093-msdk-Add-function-to-get-the-implementation-descript.patch \
           file://0094-msdk-Register-elements-based-on-the-codecs-supported.patch \
           file://0095-msdk-add-function-to-get-the-format-list.patch \
           file://0096-msdkenc-Add-function-to-dynamically-create-sink-caps.patch \
           file://0097-msdk-Add-function-to-specify-string-array-parameters.patch \
           file://0098-msdkenc-Enable-dynamic-capability-support.patch \
           file://0099-msdkenc-Remove-static-templates-in-format-setting.patch \
           file://0100-msdkenc-Support-image-formats-in-low-power-mode-for-.patch \
           file://0101-msdkenc-Add-documented-capabilities-to-expose-stable.patch \
           file://0102-msdkdec-Add-function-to-dynamically-create-sink-caps.patch \
           file://0103-msdkdec-Enable-dynamic-capability-support.patch \
           file://0104-msdkvpp-Add-function-to-dynamically-create-sink-caps.patch \
           file://0105-msdkvpp-Enable-dynamic-capability-support.patch \
           file://0106-webrtcbin-Fix-trace-log-from-value.patch \
           file://0107-msdk-Add-return-when-creating-caps-fails-for-VPP.patch \
           file://0108-msdk-Add-NumFilters-check-for-VPP-description.patch \
           file://0109-msdk-Fix-segfault-for-OneVPL-dispatcher-legacy-MSDK-.patch \
           file://0110-meson-Fix-libdrm-and-vaapi-configure-checks.patch \
           file://0111-meson-Add-a-wrap-file-for-libgudev.patch \
           file://0112-meson-Add-feature-options-for-optional-va-deps-libdr.patch \
           file://0113-msdk-Remove-unreachable-statement.patch \
           file://0114-msdk-fix-a-not-referenced-build-error.patch \
           file://0115-msdk-Fix-initialization-of-the-msdk_session.impl_idx.patch \
           file://0116-msdkmjpegdec-Fix-ColorFormat-for-BGRx-format.patch \
           file://0117-va-Sort-udev-path-name-before-register-the-va-plugin.patch \
           file://0118-va-No-need-to-sort-the-device-after-registered.patch \
           file://0119-h264parser-Define-level-enum-values.patch \
           file://0120-msdkenc-Add-properties-max-min-qp-for-I-P-B-frame-se.patch \
           file://0121-bad-docs-Add-new-properties-description-for-msdkh264.patch \
           file://0122-msdkenc-Remove-internal-vpp-function.patch \
           file://0123-msdkenc-Remove-unsupported-image-formats-for-MSDK-pa.patch \
           file://0124-docs-msdkenc-update-supported-image-formats.patch \
           file://0125-msdkdec-Fix-decoding-cases-with-resolution-change-VP.patch \
           file://0126-msdkenc-Add-a-property-to-insert-pic-timing-SEI-for-.patch \
           file://0127-va-remove-unused-parameters-in-internal-function.patch \
           file://0128-va-bump-libva-version-to-1.12.patch \
           file://0129-msdk-remove-libva-check-before-1.12.patch \
           file://0130-doc-use-gir-file-if-dependency-is-not-available.patch \
           file://0131-va-Map-drm-fourcc-into-va-fourcc-in-video-format.patch \
           file://0132-va-Add-a-helper-function-to-convert-video-drm-info-t.patch \
           file://0133-va-Extend-the-va_create_surfaces-function-to-accept-.patch \
           file://0134-va-Lower-the-message-level-for-va_export_surface_to_.patch \
           file://0135-va-Add-a-helper-function-to-detect-the-surface-modif.patch \
           file://0136-va-Replace-the-tabs-into-spaces-in-gstvacaps.h.patch \
           file://0137-va-Add-helper-functions-to-convert-GstVideoInfoDmaDr.patch \
           file://0138-va-dmabuf-allocator-to-use-GstVideoInfoDmaDrm.patch \
           file://0139-rgb2bayer-Support-video-x-bayer-10-12-14-16-bit-dept.patch \
           file://0140-bayer2rgb-Disable-in-place-transform.patch \
           file://0141-bayer2rgb-Inline-the-j-0-value.patch \
           file://0142-bayer2rgb-Fold-src_stride-into-gst_bayer2rgb_process.patch \
           file://0143-bayer2rgb-Pass-all-parameters-to-LINE-macro.patch \
           file://0144-bayer2rgb-Pass-filter-pointer-into-gst_bayer2rgb_spl.patch \
           file://0145-bayer2rgb-Add-comment-on-bayer_orc_horiz_upsample.patch \
           file://0146-bayer2rgb-Add-comments-explaining-gst_bayer2rgb_proc.patch \
           file://0147-bayer2rgb-Support-video-x-bayer-10-12-14-16-bit-dept.patch \
           file://0148-va-Fix-Windows-build.patch \
           file://0149-vapostproc-evaluate-op_flags-validations-as-boolean.patch \
           file://0150-va-basetransform-Only-need-va-pool-for-internal-usag.patch \
           file://0151-va-basetransform-Check-the-crop-meta-when-importing.patch \
           file://0152-va-basetransform-Copy-the-interested-meta-data-for-i.patch \
           file://0153-msdkenc-Add-help-functions-to-check-and-update-prope.patch \
           file://0154-msdkenc-Apply-update-functions-when-setting-property.patch \
           file://0155-va-basedec-Select-the-best-format-of-the-whole-caps.patch \
           file://0156-codecparsers-keep-naming-consistency-in-GST_H264_LEV.patch \
           file://0157-va-use-GstH264Level-enum-in-_va_h264_level_limits.patch \
           file://0158-va-Only-change-video-format-in-gst_va_video_info_fro.patch \
           file://0159-msdkh264enc-Set-profile-as-unknown-when-not-specifie.patch \
           file://0160-va-allocator-Let-pool-alloc_info-be-consitent-with-t.patch \
           file://0161-va-allocator-Rework-the-func-va_map_unlocked.patch \
           file://0162-vah265enc-Correct-the-value-of-cu_qp_delta-flag-and-.patch \
           file://0163-waylandsink-Remove-unused-instance-member.patch \
           file://0164-waylandsink-Add-a-comment-about-dmabuf-without-featu.patch \
           file://0165-waylandsink-Add-DRM-modifiers-support.patch \
           file://0166-waylandsink-Add-gst_buffer_pool_config_set_params-to.patch \
           file://0167-wayland-Export-the-_get_type-functions.patch \
           file://0168-codec2json-Add-vp82json-element.patch \
           file://0169-codec2json-Add-av12json-element.patch \
           file://0170-va-encoder-extend-prepare_output-virtual-function.patch \
           file://0171-va-encoder-Add-copy_output_data-helper-function.patch \
           file://0172-vp9bitwriter-Add-the-VP9-bit-writer-helper-functions.patch \
           file://0173-va-Implement-the-vavp9enc-plugin.patch \
           file://0174-codec2json-Delete-the-increment_tile_rows_log2-field.patch \
           file://0175-codecparsers-av1-delete-the-useless-increment_tile_r.patch \
           file://0176-codecparsers-av1-add-expected_frame_id-in-frame-head.patch \
           file://0177-codecparsers-av1-add-ref_global_motion_params-in-fra.patch \
           file://0178-codecparsers-Implement-the-AV1-bit-code-writer.patch \
           file://0179-tests-Add-the-av1-bit-code-writer-test-case.patch \
           file://0180-va-baseenc-Extend-the-create_output_buffer-to-accept.patch \
           file://0181-va-Implement-the-vaav1enc-plugin.patch \
           file://0182-va-implement-jpeg-encoder.patch \
           file://0183-d3d11-map-Y210-Y212_LE-Y412_LE-to-corresponding-DXGI.patch \
           file://0184-va-Use-GstVideoInfoDmaDrm-in-DMA-allocator-s-get-set.patch \
           file://0185-va-Use-new-dma-drm-caps-in-va-pool-when-we-setup-DMA.patch \
           file://0186-va-Improve-the-template-caps-for-DMA-use-new-drm-for.patch \
           file://0187-va-update-the-gst_va_dma_drm_info_to_video_info-to-u.patch \
           file://0188-va-Apply-the-new-DMA-format-modifier-pair-negotiatio.patch \
           file://0189-va-Apply-the-new-DMA-format-modifier-pair-negotiatio.patch \
           file://0190-va-Delete-the-usage-hint-hack-when-we-support-DRM-mo.patch \
           file://0191-va-jpegdec-Do-not-change-the-DMA-template-src-caps.patch \
           file://0192-va-encoder-Add-DMA-input-support-for-H264-and-H265-e.patch \
           file://0193-msdk-Add-a-helper-function-to-get-supported-modifier.patch \
           file://0194-msdk-Add-modifier-when-creating-dynamic-caps.patch \
           file://0195-msdk-Add-help-functions-to-handle-drm-caps.patch \
           file://0196-msdkdec-Add-modifier-support.patch \
           file://0197-msdkvpp-Add-modifier-support.patch \
           file://0198-msdkenc-Add-modifier-support.patch \
           file://0199-msdk-Remove-func-to-export-dmabuf-to-va-surface.patch \
           file://0200-msdk-Add-a-help-func-to-fix-the-map.patch \
           file://0201-msdkvpp-Add-fix_format-for-src-caps.patch \
           file://0202-va-Implement-modifier-in-vacompositor.patch \
           file://0203-va-encoder-Add-DMA-input-support-for-av1-jpeg-and-vp.patch \
           file://0001-va-Add-scaling-and-composition-pipeline-flags.patch \
           file://0001-va-compositor-Disable-tiling-for-i965-for-DMA-RGB-fo.patch \
           "
SRC_URI[sha256sum] = "eaaf53224565eaabd505ca39c6d5769719b45795cf532ce1ceb60e1b2ebe99ac"

S = "${WORKDIR}/gst-plugins-bad-${PV}"

LICENSE = "LGPL-2.1-or-later & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS += "gstreamer1.0-plugins-base libgudev libva onevpl onevpl-intel-gpu"

inherit gobject-introspection

PACKAGECONFIG ??= " \
    ${GSTREAMER_ORC} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez', '', d)} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'directfb vulkan x11', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'gl', '', d)} \
    bz2 closedcaption curl dash dtls hls openssl sbc smoothstreaming \
    sndfile ttml uvch264 webp \
    ${@bb.utils.contains('TUNE_FEATURES', 'mx32', '', 'rsvg', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'msdk', 'msdk', '', d)} \
"

PACKAGECONFIG[aom]             = "-Daom=enabled,-Daom=disabled,aom"
PACKAGECONFIG[assrender]       = "-Dassrender=enabled,-Dassrender=disabled,libass"
PACKAGECONFIG[avtp]            = "-Davtp=enabled,-Davtp=disabled,libavtp"
PACKAGECONFIG[bluez]           = "-Dbluez=enabled,-Dbluez=disabled,bluez5"
PACKAGECONFIG[bz2]             = "-Dbz2=enabled,-Dbz2=disabled,bzip2"
PACKAGECONFIG[closedcaption]   = "-Dclosedcaption=enabled,-Dclosedcaption=disabled,pango cairo"
PACKAGECONFIG[curl]            = "-Dcurl=enabled,-Dcurl=disabled,curl"
PACKAGECONFIG[dash]            = "-Ddash=enabled,-Ddash=disabled,libxml2"
PACKAGECONFIG[dc1394]          = "-Ddc1394=enabled,-Ddc1394=disabled,libdc1394"
PACKAGECONFIG[directfb]        = "-Ddirectfb=enabled,-Ddirectfb=disabled,directfb"
PACKAGECONFIG[dtls]            = "-Ddtls=enabled,-Ddtls=disabled,openssl"
PACKAGECONFIG[faac]            = "-Dfaac=enabled,-Dfaac=disabled,faac"
PACKAGECONFIG[faad]            = "-Dfaad=enabled,-Dfaad=disabled,faad2"
PACKAGECONFIG[fluidsynth]      = "-Dfluidsynth=enabled,-Dfluidsynth=disabled,fluidsynth"
PACKAGECONFIG[hls]             = "-Dhls=enabled,-Dhls=disabled,"
# Pick atleast one crypto backend below when enabling hls
PACKAGECONFIG[nettle]          = "-Dhls-crypto=nettle,,nettle"
PACKAGECONFIG[openssl]         = "-Dhls-crypto=openssl,,openssl"
PACKAGECONFIG[gcrypt]          = "-Dhls-crypto=libgcrypt,,libgcrypt"
# the gl packageconfig enables OpenGL elements that haven't been ported
# to -base yet. They depend on the gstgl library in -base, so we do
# not add GL dependencies here, since these are taken care of in -base.
PACKAGECONFIG[gl]              = "-Dgl=enabled,-Dgl=disabled,"
PACKAGECONFIG[kms]             = "-Dkms=enabled,-Dkms=disabled,libdrm"
PACKAGECONFIG[libde265]        = "-Dlibde265=enabled,-Dlibde265=disabled,libde265"
PACKAGECONFIG[libssh2]         = "-Dcurl-ssh2=enabled,-Dcurl-ssh2=disabled,libssh2"
PACKAGECONFIG[lcms2]           = "-Dcolormanagement=enabled,-Dcolormanagement=disabled,lcms"
PACKAGECONFIG[modplug]         = "-Dmodplug=enabled,-Dmodplug=disabled,libmodplug"
PACKAGECONFIG[msdk]            = "-Dmsdk=enabled -Dmfx_api=oneVPL,-Dmsdk=disabled,libgudev onevpl onevpl-intel-gpu"
PACKAGECONFIG[neon]            = "-Dneon=enabled,-Dneon=disabled,neon"
PACKAGECONFIG[openal]          = "-Dopenal=enabled,-Dopenal=disabled,openal-soft"
PACKAGECONFIG[opencv]          = "-Dopencv=enabled,-Dopencv=disabled,opencv"
PACKAGECONFIG[openh264]        = "-Dopenh264=enabled,-Dopenh264=disabled,openh264"
PACKAGECONFIG[openjpeg]        = "-Dopenjpeg=enabled,-Dopenjpeg=disabled,openjpeg"
PACKAGECONFIG[openmpt]         = "-Dopenmpt=enabled,-Dopenmpt=disabled,libopenmpt"
# the opus encoder/decoder elements are now in the -base package,
# but the opus parser remains in -bad
PACKAGECONFIG[opusparse]       = "-Dopus=enabled,-Dopus=disabled,libopus"
PACKAGECONFIG[resindvd]        = "-Dresindvd=enabled,-Dresindvd=disabled,libdvdread libdvdnav"
PACKAGECONFIG[rsvg]            = "-Drsvg=enabled,-Drsvg=disabled,librsvg"
PACKAGECONFIG[rtmp]            = "-Drtmp=enabled,-Drtmp=disabled,rtmpdump"
PACKAGECONFIG[sbc]             = "-Dsbc=enabled,-Dsbc=disabled,sbc"
PACKAGECONFIG[sctp]            = "-Dsctp=enabled,-Dsctp=disabled"
PACKAGECONFIG[smoothstreaming] = "-Dsmoothstreaming=enabled,-Dsmoothstreaming=disabled,libxml2"
PACKAGECONFIG[sndfile]         = "-Dsndfile=enabled,-Dsndfile=disabled,libsndfile1"
PACKAGECONFIG[srt]             = "-Dsrt=enabled,-Dsrt=disabled,srt"
PACKAGECONFIG[srtp]            = "-Dsrtp=enabled,-Dsrtp=disabled,libsrtp"
PACKAGECONFIG[tinyalsa]        = "-Dtinyalsa=enabled,-Dtinyalsa=disabled,tinyalsa"
PACKAGECONFIG[ttml]            = "-Dttml=enabled,-Dttml=disabled,libxml2 pango cairo"
PACKAGECONFIG[uvch264]         = "-Duvch264=enabled,-Duvch264=disabled,libusb1 libgudev"
# this enables support for stateless V4L2 mem2mem codecs, which is a newer form of
# V4L2 codec; the V4L2 code in -base supports the older stateful V4L2 mem2mem codecs
PACKAGECONFIG[v4l2codecs]      = "-Dv4l2codecs=enabled,-Dv4l2codecs=disabled,libgudev"
PACKAGECONFIG[va]              = "-Dva=enabled,-Dva=disabled,libva"
PACKAGECONFIG[voaacenc]        = "-Dvoaacenc=enabled,-Dvoaacenc=disabled,vo-aacenc"
PACKAGECONFIG[voamrwbenc]      = "-Dvoamrwbenc=enabled,-Dvoamrwbenc=disabled,vo-amrwbenc"
PACKAGECONFIG[vulkan]          = "-Dvulkan=enabled,-Dvulkan=disabled,vulkan-loader shaderc-native"
PACKAGECONFIG[wayland]         = "-Dwayland=enabled,-Dwayland=disabled,wayland-native wayland wayland-protocols libdrm"
PACKAGECONFIG[webp]            = "-Dwebp=enabled,-Dwebp=disabled,libwebp"
PACKAGECONFIG[webrtc]          = "-Dwebrtc=enabled,-Dwebrtc=disabled,libnice"
PACKAGECONFIG[webrtcdsp]       = "-Dwebrtcdsp=enabled,-Dwebrtcdsp=disabled,webrtc-audio-processing"
PACKAGECONFIG[zbar]            = "-Dzbar=enabled,-Dzbar=disabled,zbar"
PACKAGECONFIG[x11]             = "-Dx11=enabled,-Dx11=disabled,libxcb libxkbcommon"
PACKAGECONFIG[x265]            = "-Dx265=enabled,-Dx265=disabled,x265"

GSTREAMER_GPL = "${@bb.utils.filter('PACKAGECONFIG', 'faad resindvd x265', d)}"

EXTRA_OEMESON += " \
    -Ddoc=disabled \
    -Daes=enabled \
    -Dcodecalpha=enabled \
    -Ddecklink=enabled \
    -Ddvb=enabled \
    -Dfbdev=enabled \
    -Dipcpipeline=enabled \
    -Dshm=enabled \
    -Dtranscode=enabled \
    -Dandroidmedia=disabled \
    -Dapplemedia=disabled \
    -Dasio=disabled \
    -Dbs2b=disabled \
    -Dchromaprint=disabled \
    -Dd3dvideosink=disabled \
    -Dd3d11=disabled \
    -Ddirectsound=disabled \
    -Ddts=disabled \
    -Dfdkaac=disabled \
    -Dflite=disabled \
    -Dgme=disabled \
    -Dgs=disabled \
    -Dgsm=disabled \
    -Diqa=disabled \
    -Dkate=disabled \
    -Dladspa=disabled \
    -Dldac=disabled \
    -Dlv2=disabled \
    -Dmagicleap=disabled \
    -Dmediafoundation=disabled \
    -Dmicrodns=disabled \
    -Dmpeg2enc=disabled \
    -Dmplex=disabled \
    -Dmusepack=disabled \
    -Dnvcodec=disabled \
    -Dopenexr=disabled \
    -Dopenni2=disabled \
    -Dopenaptx=disabled \
    -Dopensles=disabled \
    -Donnx=disabled \
    -Dqroverlay=disabled \
    -Dsoundtouch=disabled \
    -Dspandsp=disabled \
    -Dsvthevcenc=disabled \
    -Dteletext=disabled \
    -Dwasapi=disabled \
    -Dwasapi2=disabled \
    -Dwildmidi=disabled \
    -Dwinks=disabled \
    -Dwinscreencap=disabled \
    -Dwpe=disabled \
    -Dzxing=disabled \
"

export OPENCV_PREFIX = "${STAGING_DIR_TARGET}${prefix}"

FILES:${PN}-freeverb += "${datadir}/gstreamer-1.0/presets/GstFreeverb.prs"
FILES:${PN}-opencv += "${datadir}/gst-plugins-bad/1.0/opencv*"
FILES:${PN}-transcode += "${datadir}/gstreamer-1.0/encoding-profiles"
FILES:${PN}-voamrwbenc += "${datadir}/gstreamer-1.0/presets/GstVoAmrwbEnc.prs"

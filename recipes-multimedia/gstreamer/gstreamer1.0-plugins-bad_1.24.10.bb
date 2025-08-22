require gstreamer1.0-plugins-common.inc
require gstreamer1.0-plugins-license.inc

SUMMARY = "'Bad' GStreamer plugins and helper libraries "
HOMEPAGE = "https://gstreamer.freedesktop.org/"
BUGTRACKER = "https://gitlab.freedesktop.org/gstreamer/gst-plugins-bad/-/issues"

SRC_URI = "https://gstreamer.freedesktop.org/src/gst-plugins-bad/gst-plugins-bad-${PV}.tar.xz \
           file://0001-fix-maybe-uninitialized-warnings-when-compiling-with.patch \
           file://0002-avoid-including-sys-poll.h-directly.patch \
           file://0004-opencv-resolve-missing-opencv-data-dir-in-yocto-buil.patch \
           file://0001-vah265enc-Expand-log2_max_pic_order_cnt-if-needed.patch \
           file://0002-vah265enc-Improve-B-pyramid-mode-in-HEVC.patch \
           file://0003-vah265enc-Set-backward_num-to-1-in-low-delay-mode.patch \
           file://0004-MSDK-Set-the-job-type-when-create-context-from-exter.patch \
           file://0005-vp9bitwriter-Add-the-VP9-bit-writer-helper-functions.patch \
           file://0006-test-add-vp9-bitwriter-test-case.patch \
           file://0007-va-Implement-the-vavp9enc-plugin.patch \
           file://0008-va-encoder-Set-the-quality_factor-parameter-in-rate-.patch \
           file://0009-va-encoder-Enable-ICQ-and-QVBR-mode-in-rate-control-.patch \
           file://0010-va-h264enc-enable-ICQ-and-QVBR-modes.patch \
           file://0011-va-h265enc-enable-ICQ-and-QVBR-modes.patch \
           file://0012-va-vp9enc-enable-ICQ-and-QVBR-modes.patch \
           file://0013-va-av1enc-enable-ICQ-and-QVBR-modes.patch \
           file://0014-va-encoder-update-the-bitrate-change-correctly.patch \
           file://0015-va-vp9-av1-enc-Do-not-use-g_slice_new-to-create-fram.patch \
           file://0016-va-av1enc-Correct-the-flags-for-registering-properti.patch \
           file://0017-va-vp9enc-Correct-the-flags-for-registering-properti.patch \
           file://0018-va-av1-vp9-enc-Use-g_free-to-free-frames.patch \
           file://0019-msdk-Fix-session-close-failure.patch \
           file://0020-msdkenc-Set-VideoFullRange-according-to-input-colori.patch \
           file://0021-msdkvpp-Set-colorimetry-for-src-caps.patch \
           file://0022-msdk-Fix-mjpeg-BGRx-encode.patch \
           file://0023-va-vp9enc-Fix-the-frame-size-not-enough-issue-for-su.patch \
           file://0024-va-vp9enc-Adjust-the-coded-buffer-size-to-avoid-fail.patch \
           file://0025-va-encoder-Fix-the-unit-of-bitrate-in-debug-log-mess.patch \
           file://0026-va-vp9enc-Avoid-reopen-encoder-or-renegotiate.patch \
           file://0027-va-h264enc-set-the-reconf-flag-when-cpb_size-updated.patch \
           file://0028-va-h264enc-Change-the-set_property-to-make-it-atomic.patch \
           file://0029-va-h265enc-set-the-reconf-flag-when-cpb_size-updated.patch \
           file://0030-va-h265enc-Change-the-set_property-to-make-it-atomic.patch \
           file://0031-va-vp9-av1-enc-reconfigure-when-properties-change.patch \
           file://0032-va-vp9enc-Change-the-set_property-to-make-it-atomic.patch \
           file://0033-va-av1enc-Change-the-set_property-to-make-it-atomic.patch \
           file://0034-examples-vaenc-dynamic-add-vp9-av1-and-low-power-tes.patch \
           file://0035-va-encoders-don-t-assert-at-target-percentage-when-Q.patch \
           file://0036-examples-vaenc-dynamic-ignore-bitrate-change-with-IC.patch \
           file://0037-examples-vaenc-dynamic-support-target-percentage-cha.patch \
           file://0038-msdk-Add-main-422-12-profile-to-hevc.patch \
           file://0039-vavp9enc-Preserve-PTS-and-other-frame-metadata.patch \
           file://0040-msdk-Add-Y212-format-to-hevc-encoder-static-raw-caps.patch \
           file://0041-vavp9enc-Do-not-use-base-class-video-info-to-calcula.patch \
           file://0042-vah26-4-5-enc-Set-the-qp_p-and-qp_b-to-qp_i-value-in.patch \
           file://0043-vaenc-Allow-to-set-the-max-qp-and-min-qp-for-QVBR-an.patch \
           file://0044-va-encoder-Use-GstVaEncFrame-as-the-base-object-for-.patch \
           file://0045-va-libs-Add-va_check_surface_has_status-helper-funct.patch \
           file://0046-va-libs-Use-va_check_surface_has_status-to-implement.patch \
           file://0047-va-encoder-Do-not-continue-when-push_buffer-gets-err.patch \
           file://0048-va-baseenc-Add-a-preferred_output_delay-field-for-GP.patch \
           file://0049-va-baseenc-Add-is_live-field-to-check-the-live-strea.patch \
           file://0050-va-h264enc-Set-preferred_output_delay-value-to-incre.patch \
           file://0051-va-h265enc-Set-preferred_output_delay-value-to-incre.patch \
           file://0052-va-vp9enc-Set-preferred_output_delay-value-to-increa.patch \
           file://0053-va-av1enc-Set-preferred_output_delay-value-to-increa.patch \
           file://0054-vabaseenc-Set-the-correct-min_buffers-for-propose_al.patch \
           file://0055-examples-va-add-option-for-enabling-alive-stream.patch \
           file://0056-vah26xenc-factorize-the-encoder-frame-setup.patch \
           file://0057-vah26-4-5-enc-No-need-to-assert-i-0-in-frame_setup_f.patch \
           file://0058-msdkcaps-fix-ill-format-string.patch \
           file://0059-va-baseenc-Set-the-trellis-parameter-anyway.patch \
           file://0060-vah264enc-Set-the-trellis-only-when-HW-supports-it.patch \
           file://0061-vah264enc-Init-missing-fields-in-reset_state.patch \
           file://0062-vah265enc-Set-the-trellis-only-when-HW-supports-it.patch \
           file://0063-vabasetranform-Initialize-the-allocation-related-var.patch \
           file://0064-vabasedec-Initialize-the-allocation-related-variable.patch \
           file://0065-vacompositor-Initialize-the-allocation-related-varia.patch \
           file://0066-vapostproc-Do-not-append-ANY-caps-into-pad-template.patch \
           file://0067-vadeinterlace-Do-not-append-ANY-caps-into-pad-templa.patch \
           file://0068-vavpp-simplify-gst_va_vpp_transform_caps.patch \
           file://0069-msdk-Add-new-driver-name-xe.patch \
           file://0070-va-h264enc-Make-the-level-table-aligned.patch \
           file://0071-vah264enc-Improve-B-pyramid-mode-in-H264.patch \
           file://0072-vah264enc-Fix-intra-only-stream-bug.patch \
           file://0073-va-deinterlace-Push-the-forgotten-leading-frames-if-.patch \
           file://0074-va-deinterlace-Do-not-use-the-backward-reference.patch \
           file://0075-vah26xenc-use-gst_h26x_slice_type_to_string.patch \
           file://0076-vah264enc-update-b_pryamid-property-if-it-changes.patch \
           file://0077-va-replace-d-for-u-format-for-system_frame_number-gu.patch \
           file://0078-vah264enc-fix-typo.patch \
           file://0079-codecparsers-Implement-the-jpeg-bit-code-writer.patch \
           file://0080-tests-Add-the-jpeg-bit-code-writer-test-case.patch \
           file://0081-va-baseenc-Check-the-bitrate-property-before-get-its.patch \
           file://0082-va-Implement-the-vajpegenc-plugin.patch \
           file://0083-vasurfaceimage-log-surface-status-string.patch \
           file://0084-vajpegenc-set-interlace-mode-colorspace-and-sampling.patch \
           file://0085-va-videoformat-Correct-NV21-s-BPP.patch \
           file://0086-va-vpp-Use-gst_caps_replace-to-operate-the-filter_ca.patch \
           file://0087-va-jpegenc-Fix-a-memory-leak-when-filter-sink-caps.patch \
           file://0088-h265bitwriter-Add-check-for-data-size-to-avoid-overf.patch \
           file://0089-h264bitwriter-Add-check-for-data-size-to-avoid-overf.patch \
           file://0090-va-decoder-Delete-all-the-internal-locks.patch \
           file://0091-msdkenc-Guard-the-read-of-thiz-initialized-with-the-.patch \
           file://0092-libs-codecparsers-Add-the-VVC-H266-parser-header-fil.patch \
           file://0093-libs-codecparsers-Implement-the-VVC-H266-parser-part.patch \
           file://0094-libs-codecparsers-Implement-the-VVC-H266-parser-part.patch \
           file://0095-tests-Add-the-VVC-H266-parser-test-cases.patch \
           file://0096-va-h264dec-Allow-extended-profile-decoding.patch \
           file://0097-va-display-Optimize-out-some-property-indirection.patch \
           file://0098-libs-codecparsers-H266-GstH266RefPicListStruct-s-abs.patch \
           file://0099-libs-codecparsers-Add-the-missing-ilrp_idx-field-in-.patch \
           file://0100-h266parse-Add-the-new-h266parse-element.patch \
           file://0101-test-Add-the-h266parse-element-test.patch \
           file://0102-Doc-Update-the-plugin-document-for-h266parse.patch \
           file://0103-av1parse-Fix-a-typo-in-the-comments-about-its-usage.patch \
           file://0104-codecs-Add-the-H266-VVC-decoder-base-class.patch \
           file://0105-va-Implement-the-VA-h266-decoder.patch \
           file://0106-va-encoder-disable-usage-hint-because-of-iHD-bug.patch \
           file://0107-msdkdec-Apply-dynamic-allocation-for-VPL-2.9.patch \
           file://0108-msdkenc-Add-VPL-string-API-option.patch \
           file://0109-msdkdec-Do-not-use-aligned-value-to-set-allocation-c.patch \
           file://0110-_opencv-data-path.patch \
           file://0111-Skip-failing-tests.patch \
           file://0112-va-compositor-Update-to-multiple-input-direct-write-.patch \
           file://0113-va-Add-scaling-and-composition-pipeline-flags.patch \
           file://0114-va-compositor-Disable-tiling-for-i965-for-DMA-RGB-fo.patch \
           file://0115-Set-background-as-black-screen.patch \
           file://0116-vaav1enc-Move-repeat-header-data-to-a-dedicated-buff.patch \
           file://0117-Optimize-VP9-preferred_output_delay-value.patch \
           file://0001-msdk-Use-local-cflags-for-configuration.patch \
           file://0002-msdk-Add-extra-build-for-another-msdk-onevpl.patch \
           file://0001-msdk-adding-back-libdrm-dependecy.patch \
           file://0001-Enable-SCC-tool-on-vaav1enc.patch \
           file://0001-msdkav1enc-Add-intrabc-and-palette-for-scc-encode.patch \
           file://0001-va-Add-AVC-10Bit-decode.patch \
           "
SRC_URI[sha256sum] = "1707e3103950c9baed364a8af2ba0495d6b113fcd36e1062dda5f582b8f8904d"

S = "${WORKDIR}/gst-plugins-bad-${PV}"

LICENSE = "LGPL-2.1-or-later & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS += "gstreamer1.0-plugins-base libgudev libva libvpl vpl-gpu-rt"

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
PACKAGECONFIG[msdk]            = "-Dmsdk=enabled -Dmfx_api=MSDK,-Dmsdk=disabled,libgudev intel-mediasdk"
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
FILES:${PN}-msdk-vpl += "${libdir}/gstreamer-1.0-msdk-onevpl/*.so"
PACKAGES += "${PN}-msdk-vpl"

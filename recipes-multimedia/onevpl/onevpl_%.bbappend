FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://0001-Fix-rdrm-NV12-and-P010-render-for-MTL.patch \
                   file://0002-Encode-more-frame-than-shortest-video-frames.patch \
                   file://0003-Fix-NV12-wayland-render-for-MTL.patch \
                   file://0004-Fix-dGfx-option-for-Linux.patch \
                   file://0005-Fix-rdrm-to-use-card-node-instead-of-render-node.patch \
                   file://0006-Fix-rdrm-tile4-selection-based-DRM-KMS-query.patch \
                   file://0007-Remove-unused-codes.patch \
                   file://0008-Enable-JPEG-BGR4-encode.patch \
                 "

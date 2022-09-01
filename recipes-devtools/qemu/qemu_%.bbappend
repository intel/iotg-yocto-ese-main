FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
        file://0001-edid-Added-support-for-4k-60-Hz-monitor.patch \
        file://0002-ui-gtk-detach-VCS-for-additional-guest-displays.patch \
        file://0003-ui-gtk-a-new-array-param-monitor-to-specify-the-targ.patch \
        file://0004-virtio-gpu-update-done-only-on-the-scanout-associate.patch \
        file://0005-ui-gtk-adds-status-bar-for-expressing-ups-and-fps.patch \
        file://0006-ui-gtk-calling-gd_gl_frame_counter-at-every-draw-swa.patch \
        file://0007-virtio-gpu-call-dpy_gl_frame_counter-at-every-guest-.patch \
        file://0001-virtio-gpu-not-deleting-resources-when-resetting-GPU.patch \
        file://0001-ui-gtk-set-the-size-of-detached-window-to-default.patch \
       "

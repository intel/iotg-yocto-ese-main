FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = "\
        file://0001-edid-Added-support-for-4k-60-Hz-monitor.patch \
        file://0002-ui-gtk-new-param-monitor-to-specify-target-monitor-f.patch \
        file://0003-ui-gtk-detach_all-option-for-making-all-VCs-detached.patch \
        file://0004-ui-gtk-specify-detached-window-s-size-and-location.patch \
        file://0005-ui-gtk-adds-status-bar-for-expressing-ups-and-fps.patch \
        file://0006-ui-gtk-calling-gd_gl_frame_counter-at-every-draw-swa.patch \
        file://0007-virtio-gpu-call-dpy_gl_frame_counter-at-every-guest-.patch \
        file://0001-virtio-gpu-update-done-only-on-the-scanout-associate.patch \
       "

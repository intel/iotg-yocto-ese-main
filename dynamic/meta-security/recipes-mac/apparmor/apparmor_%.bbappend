python() {
    if bb.utils.contains('IMAGE_FEATURES', 'read-only-rootfs', '1', '0', d) == '1':
        d.delVar("pkg_postinst_ontarget_apparmor")
}

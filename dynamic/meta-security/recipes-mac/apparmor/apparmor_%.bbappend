# Our systemd unit depends on it, do not delete
python rm_sysvinit_initddir(){
}

do_install:append(){
        if [ "${baselib}" = "lib64" ]; then
                mv ${D}/usr/lib/python* ${D}/usr/lib64/
        fi
}

python() {
    if bb.utils.contains('IMAGE_FEATURES', 'read-only-rootfs', '1', '0', d) == '1':
        d.delVar("pkg_postinst_ontarget:apparmor")
}

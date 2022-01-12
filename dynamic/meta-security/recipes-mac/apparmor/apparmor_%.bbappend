# Our systemd unit depends on it, do not delete
python rm_sysvinit_initddir(){
}

# make sure service files do not have executable mode
do_install:append(){
        if [ "${VIRTUAL-RUNTIME_init_manager}" = "systemd" ]; then
                chmod 0644 ${D}${systemd_system_unitdir}/apparmor.service
        fi
        if [ "${baselib}" = "lib64" ]; then
                install -m 0755 -d ${D}/usr/lib64
                mv ${D}/usr/lib/* ${D}/usr/lib64/
                rm -rf ${D}/usr/lib
        fi
}

python() {
    if bb.utils.contains('IMAGE_FEATURES', 'read-only-rootfs', '1', '0', d) == '1':
        d.delVar("pkg_postinst_ontarget:apparmor")
}

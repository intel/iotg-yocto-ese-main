# Our systemd unit depends on it, do not delete
python rm_sysvinit_initddir(){
}

# make sure service files do not have executable mode
do_install_append(){
        if [ "${VIRTUAL-RUNTIME_init_manager}" = "systemd" ]; then
                chmod 0644 ${D}${systemd_system_unitdir}/apparmor.service
        fi
}

python() {
    if bb.utils.contains('IMAGE_FEATURES', 'read-only-rootfs', '1', '0', d) == '1':
        d.delVar("pkg_postinst_ontarget_apparmor")
}

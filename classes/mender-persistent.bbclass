# this fixup should be inherited after rootfs-postcommands

fakeroot python do_mender_persistent_fixup(){
    import os, shutil, stat, subprocess

    rootfs = d.getVar("IMAGE_ROOTFS")
    etc = os.path.join(rootfs, "etc")
    var = os.path.join(rootfs, "var")
    data = os.path.join(rootfs, "data")
    home = os.path.join(rootfs, "home")
    share = os.path.join(rootfs, "usr/share")
    managed = os.path.join(rootfs, "usr/share/intel/managed")
    persistent = os.path.join(data, "persistent")
    mode = stat.S_IRWXU | stat.S_IRGRP | stat.S_IXGRP | stat.S_IROTH | stat.S_IXOTH

    # is this really a mender image with /data?
    is_mender = bb.utils.contains_any('DISTRO_FEATURES', 'mender-install mender-install', "1", "0", d)
    if not int(is_mender):
        return

    # sanity check
    if not (rootfs and etc and var and data and home and persistent and share and managed):
        bb.error("[%s] paths are bad\n" % d.getVar("PN"))

    # generate ld.so.cache
    subprocess.call(["ldconfig", "-r", rootfs, "-c", "new", "-v" ])

    # force regenerate at boot time
    os.remove(os.path.join(etc, "mender/mender.conf"))

    # create persistent data area
    if not os.path.isdir(data):
        bb.error("[%s] mender data directory does not exist\n" % d.getVar("PN"))

    if not os.path.isdir(persistent):
        os.mkdir(persistent, mode)

    # BSP managed content
    os.makedirs(managed, mode, exist_ok=True)

    # handle mender artifact version info specially
    # /etc/mender/artifact_info -> /usr/share/intel/managed/artifact_info
    info_file = os.path.join(etc, "mender/artifact_info")
    info_dest = os.path.join(rootfs, os.path.join(managed, "artifact_info"))
    if not (info_file and info_dest):
        bb.error("[%s] failed to move mender info data\n" % d.getVar("PN"))
    shutil.move(info_file, info_dest)
    os.symlink("/usr/share/intel/managed/artifact_info", info_file)

    # put apparmor under BSP management
    apparmor = os.path.join(etc, "apparmor")
    apparmor_d = os.path.join(etc, "apparmor.d")
    if os.path.isdir(apparmor) and os.path.isdir(apparmor):
        # handle cache dir
        os.mkdir(os.path.join(apparmor_d, "cache"), mode)
        os.mkdir(os.path.join(persistent, "apparmor.d.cache"), mode)
        # move the rest
        shutil.move(apparmor, managed)
        shutil.move(apparmor_d, managed)
        os.mkdir(apparmor, mode)
        os.mkdir(apparmor_d, mode)

    # move everything else to persistent /data
    shutil.move(etc, persistent)
    shutil.move(var, persistent)
    shutil.move(home, persistent)

    # create empty dummies
    os.mkdir(etc, mode)
    os.mkdir(var, mode)
    os.mkdir(home, mode)

    # fix .manifest generation, at least for rpm
    # TODO: other package managers
    rpmlib = "var/lib/rpm"
    rpm_persist = os.path.join(persistent,rpmlib)
    if os.path.isdir(rpm_persist):
        os.makedirs(os.path.join(rootfs, "var/lib"), mode)
        os.symlink("../../data/persistent/var/lib/rpm", os.path.join(rootfs,rpmlib))
}

python(){
    d.appendVar('ESE_IMAGE_CALLS', ' do_mender_persistent_fixup;')
}

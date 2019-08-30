python do_kernel_module_fixups(){
    import subprocess;

    rootfs = d.getVar("IMAGE_ROOTFS")
    moddir = os.path.join(rootfs, "lib/modules")
    bb.note("[%s] rootfs = %s; moddir = %s\n" % (d.getVar("PN"), rootfs, moddir))
    if rootfs and not os.path.isdir(moddir):
        return

    for entry in os.listdir(moddir):
        if os.path.isdir(os.path.join(moddir,entry)):
            bb.note("[%s] running depmod on %s\n" % (d.getVar("PN"), entry))
            subprocess.call(["depmod", "-a", "-b", rootfs, entry ])
}

ROOTFS_POSTPROCESS_COMMAND += "do_kernel_module_fixups;"

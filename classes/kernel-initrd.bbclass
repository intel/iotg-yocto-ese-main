# The Yocto initramfs bundling mechanism restores the original kernel
# without the initramfs, but we do want the version with the initramfs
# installed with kernel-image. We modify the config file to pull in the
# cpio image file directly instead.

python do_put_initramfs(){
    import re
    import shutil
    import os
    import os.path

    image_cpio = d.getVar("INITRAMFS_IMAGE_PATH")
    if not image_cpio:
        image_cpio = d.expand("${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE}-${MACHINE}.cpio")

    if not os.path.isfile(image_cpio):
        bb.error("initramfs %s not found!" % image_cpio)

    cfgfile = d.expand("${B}/.config")
    cfgfile_temp = d.expand("${WORKDIR}/.config.initrd")
    cfg_in = open(cfgfile, 'r')
    cfg_out = os.open(cfgfile_temp, os.O_WRONLY|os.O_CREAT|os.O_TRUNC)

    for line in cfg_in:
        lineout = re.sub(r'@CONFIG_INITRAMFS_SOURCE@', image_cpio, line)
        os.write(cfg_out, bytes(lineout, 'utf_8'))
    os.close(cfg_out)
    shutil.move(cfgfile_temp, cfgfile)
}

python(){
    import re
    image = d.getVar("INITRAMFS_IMAGE_RECIPE")
    if image:
        if re.match("^(mc|multiconfig):", image):
            flag = "mcdepends"
        else:
            flag = "depends"
        d.appendVarFlag("do_kernel_configme", flag, " " + image + ":do_image_complete")
        d.appendVarFlag("do_kernel_configme", "postfuncs", " do_put_initramfs")
        # workaround to force rebuild if image updated
        d.appendVarFlag("do_compile", flag, " " + image + ":do_image_complete")
}

DEPENDS_append = " intel-microcode"
SYSROOT_DIRS_append = " ${nonarch_base_libdir}/firmware"

python do_put_iucode(){
    import re
    import os
    import shutil

    fw_path = d.expand("${STAGING_DIR_TARGET}/lib/firmware")
    cfgfile = d.expand("${B}/.config")
    cfgfile_temp = d.expand("${WORKDIR}/.config.microcode")
    ucode_path = d.expand("${STAGING_DIR_TARGET}/lib/firmware/intel-ucode")
    fw_list = []

    for entry in os.listdir(path=ucode_path):
        if (os.path.isfile(os.path.join(ucode_path, entry))):
            fw_list.append(os.path.join("intel-ucode", entry))
    fw_str = " ".join(fw_list)

    cfg_in = open(cfgfile, 'r')
    cfg_out = os.open(cfgfile_temp, os.O_WRONLY|os.O_CREAT|os.O_TRUNC)

    for line in cfg_in:
        lineout = re.sub(r'@CONFIG_EXTRA_FIRMWARE@', fw_str, line)
        lineout = re.sub(r'@CONFIG_EXTRA_FIRMWARE_DIR@', fw_path, lineout)
        os.write(cfg_out, bytes(lineout, 'utf_8'))
    os.close(cfg_out)
    shutil.move(cfgfile_temp, cfgfile)
}

python(){
    d.appendVarFlag("do_kernel_configme", "postfuncs", " do_put_iucode")
}


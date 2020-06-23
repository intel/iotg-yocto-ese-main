FILES_mesa-megadriver_append = " ${sysconfdir}"
GALLIUMDRIVERS_append_x86 = ",iris"
GALLIUMDRIVERS_append_x86-64 = ",iris"

#fix comma issue
python(){
    dri = d.getVar('DRIDRIVERS')
    d.setVar('DRIDRIVERS', dri.strip(','))

    vulk = d.getVar('VULKAN_DRIVERS')
    d.setVar('VULKAN_DRIVERS', vulk.strip(','))
}


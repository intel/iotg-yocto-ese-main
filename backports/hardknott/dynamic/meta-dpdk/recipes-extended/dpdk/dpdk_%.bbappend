#upstream recipe is disabling libvirt by default
#enabling it through bbappend
PACKAGECONFIG_append_class-target = " libvirt"

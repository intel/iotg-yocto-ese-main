PACKAGECONFIG_GL += "${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'opengl', '', d)}"

PACKAGECONFIG += "  ${@bb.utils.contains('DISTRO_FEATURES', 'onevpl', 'onevpl', '', d)} "
PACKAGECONFIG[onevpl]          = "-Dmfx_api=oneVPL,-Dmfx_api=MSDK,onevpl"

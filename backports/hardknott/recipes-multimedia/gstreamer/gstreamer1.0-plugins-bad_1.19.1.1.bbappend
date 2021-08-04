PACKAGECONFIG += "\
              ${@bb.utils.contains('DISTRO_FEATURES', 'onevpl', 'onevpl', '', d)} \
              "
PACKAGECONFIG[onevpl] = "-Dmsdk=enabled -Dmfx_api=oneVPL,-Dmsdk=disabled,libgudev onevpl onevpl-intel-gpu"

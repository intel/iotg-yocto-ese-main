FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
# remove build time rpath
SRC_URI_append = " file://remove-libstdc-vtv-rpath.patch"

# dunfell patch updated from gatesgarth
FILESEXTRAPATHS_prepend := "${@bb.utils.contains('LAYERSERIES_CORENAMES', 'dunfell', '${THISDIR}/files-dunfell:', '', d)}"

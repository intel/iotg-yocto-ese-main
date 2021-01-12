# only needed for dunfell
require ${@bb.utils.contains('LAYERSERIES_CORENAMES', 'dunfell', './kmod-xz.inc', '', d)}

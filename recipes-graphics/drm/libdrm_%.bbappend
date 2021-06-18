FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " file://0002-intel-Add-intel_is_adlp.patch \
                   file://0003-intel-bufmgr_gem-Add-stride-restriction-to-adl-p.patch \
                 "

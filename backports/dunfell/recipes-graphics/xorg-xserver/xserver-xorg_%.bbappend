FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

python() {
    import re

    version = d.getVar('PV')
    if re.match('^1.20.4', version):
        d.appendVar('SRC_URI', ' file://0001-dri2-Sync-i965_pci_ids.h-from-mesa.patch')
        d.appendVar('SRC_URI', ' file://0001-Add-TGL-ID-for-enabling-the-E2E-GEN12-Compression-and-Decompression.patch')
}

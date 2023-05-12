FILESEXTRAPATHS:prepend := "${THISDIR}/xdp-tools:"

# For libbpf > 0.6, libdpdk meson build require libxdp to enable af_xdp
# Add a xdp_desc xtime field to specify packet LaunchTime for drivers that support the feature.
SRC_URI:append = "file://0001-fix_xdp_desc.patch"
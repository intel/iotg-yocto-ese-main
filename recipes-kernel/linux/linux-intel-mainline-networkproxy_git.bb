require recipes-kernel/linux/linux-intel-mainline_git.bb
SRC_URI_append = " file://bsp/${BSP_SUBTYPE}/networkproxy.scc"
LINUX_KERNEL_TYPE_append = "-networkproxy"

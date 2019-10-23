KERNEL_WRAP ?= "linux-intel-mainline-5.4"
require recipes-kernel/linux/${KERNEL_WRAP}_git.bb
SRC_URI_append = " file://bsp/${BSP_SUBTYPE}/networkproxy.scc"
LINUX_KERNEL_TYPE_append = "-networkproxy"

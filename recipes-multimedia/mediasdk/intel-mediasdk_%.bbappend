FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
	file://0001-fix-MSDK-weston-flickering-issue.patch \
	file://0001-enable-10bit-render-support-in-wayland.patch \
	file://0001-Don-t-try-to-autheticate-with-render-node.patch \
        "

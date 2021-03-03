SRC_URI_append = "file://0001-taprio-Add-support-for-setting-Frame-Preemption-Queu.patch \
                  file://0001-tc-taprio-increase-the-sched-entry-msg-size.patch \
                  file://0001-taprio-Add-support-for-preempt-parameter.patch \
"


FILESEXTRAPATHS_prepend := "${THISDIR}/iproute2:"

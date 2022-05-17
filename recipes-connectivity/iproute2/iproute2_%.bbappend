FILESEXTRAPATHS:prepend := "${THISDIR}/iproute2:"

SRC_URI:append = "file://0001-taprio-Add-support-for-preempt-parameter.patch \
                  file://0001-taprio-Add-support-for-the-SetAndHold-and-SetAndRele.patch \
                  file://0001-taprio-Add-support-for-setting-Frame-Preemption-Queu.patch \
"

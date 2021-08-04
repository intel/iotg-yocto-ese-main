FILESEXTRAPATHS_prepend := "${THISDIR}/iproute2:"

SRC_URI_append = "file://0001-taprio-Add-support-for-preempt-parameter.patch \
                  file://0001-taprio-Add-support-for-the-SetAndHold-and-SetAndRele.patch \
"

require recipes-connectivity/bluez5/bluez5.inc

bluez5_url ?= "git://git.kernel.org/pub/scm/bluetooth/bluez;branch=master;protocol=https"
bluez5_srcrev ?= "c8cd5b04ccd865deeb90b70ea649c0b6cc0385e6"
bluez5_pv ?= "5.51+git${SRCPV}"

SRC_URI="${bluez5_url}"

SRC_URI_append = " file://out-of-tree.patch \
                   file://0001-Makefile.am-Fix-a-race-issue-for-tools.patch \
                   file://init \
                   file://run-ptest \
                   file://0001-tests-add-a-target-for-building-tests-without-runnin.patch \
                   file://0001-test-gatt-Fix-hung-issue.patch \
                "

PV = "${bluez5_pv}"
SRCREV = "${bluez5_srcrev}"

S = "${WORKDIR}/git" 
# noinst programs in Makefile.tools that are conditional on READLINE
# support
NOINST_TOOLS_READLINE ?= " \
    ${@bb.utils.contains('PACKAGECONFIG', 'deprecated', 'attrib/gatttool', '', d)} \
    tools/obex-client-tool \
    tools/obex-server-tool \
    tools/bluetooth-player \
    tools/obexctl \
    tools/btmgmt \
"

# noinst programs in Makefile.tools that are conditional on TESTING
# support
NOINST_TOOLS_TESTING ?= " \
    emulator/btvirt \
    emulator/b1ee \
    emulator/hfp \
    peripheral/btsensor \
    tools/3dsp \
    tools/mgmt-tester \
    tools/gap-tester \
    tools/l2cap-tester \
    tools/sco-tester \
    tools/smp-tester \
    tools/hci-tester \
    tools/rfcomm-tester \
    tools/bnep-tester \
    tools/userchan-tester \
"

NOINST_TOOLS_BT ?= " \
    tools/bdaddr \
    tools/avinfo \
    tools/avtest \
    tools/scotest \
    tools/amptest \
    tools/hwdb \
    tools/hcieventmask \
    tools/hcisecfilter \
    tools/btinfo \
    tools/btsnoop \
    tools/btproxy \
    tools/btiotest \
    tools/bneptest \
    tools/mcaptest \
    tools/cltest \
    tools/oobtest \
    tools/advtest \
    tools/seq2bseq \
    tools/nokfw \
    tools/create-image \
    tools/eddystone \
    tools/ibeacon \
    tools/btgatt-client \
    tools/btgatt-server \
    tools/test-runner \
    tools/check-selftest \
    tools/gatt-service \
    profiles/iap/iapd \
    ${@bb.utils.contains('PACKAGECONFIG', 'btpclient', 'tools/btpclient', '', d)} \
"



# Fix against Kirkstone build. Remove this bbappend when fix is available in upstream meta layer.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:pn-mender-client = "git://github.com/mendersoftware/mender;protocol=https;branch=master"
SRC_URI:append:pn-mender-client = " file://0001-Upgrade-openssl-dependency.patch"

# Tag '3.3.x' https://github.com/mendersoftware/mender/commits/3.3.x
SRCREV:pn-mender-client = "a62dcb734d7b552963972bc522f177b1f1f8a316"

DEFAULT_PREFERENCE = "10"

#Connman service is stopped due to dhcpcd service.
#This is to prevent and run the connman.service
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://remove-conflicts-for-connman.patch;patchdir=${WORKDIR}"


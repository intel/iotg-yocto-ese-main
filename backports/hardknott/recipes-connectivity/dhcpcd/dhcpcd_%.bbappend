#Connman service is stopped due to dhcpcd service.
#This is to prevent and run the connman.service
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " file://remove-conflicts-for-connman.patch;patchdir=${WORKDIR}"


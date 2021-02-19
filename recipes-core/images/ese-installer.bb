DESCRIPTION = "Installer Image helper recipe"
LICENSE = "MIT"
ESE_INSTALLER_WKS_FILE ??= "ese-installer.wks.in"
WKS_FILE_forcevariable = "${ESE_INSTALLER_WKS_FILE}"
inherit core-image
INHIBIT_DEFAULT_DEPS = "1"

# workaround to fix the oe-core broken issue on spice-gtk
# due to conflicting dependency requirements
# Error: 
# Problem 1: package spice-gtk-dev-0.42-r0.corei7_64 requires spice-protocol-dev, but none of the providers can be installed
#  - conflicting requests
#  - nothing provides spice-protocol = 0.14.4-r0 needed by spice-protocol-dev-0.14.4-r0.corei7_64
#Problem 2: package libspice-server-dev-0.14.2+git0+7cbd70b931_4fc4c2db36-r0.corei7_64 requires spice-protocol-dev, but none of the providers can be installed
#  - conflicting requests
#  - nothing provides spice-protocol = 0.14.4-r0 needed by spice-protocol-dev-0.14.4-r0.corei7_64
# The below line will create empty spice-protocol to solve the dependency
# 
# oe-core commit which caused the error is below
# https://github.com/openembedded/openembedded-core/commit/e7d3e02a624f7ce23d012bb11ad1df2049066b37
ALLOW_EMPTY:${PN} = "1"

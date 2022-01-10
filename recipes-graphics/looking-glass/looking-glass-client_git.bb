DESCRIPTION = "Looking Glass is an open source application that allows the use of a KVM (Kernel-based Virtual Machine) configured for VGA PCI Pass-through without an attached physical monitor, keyboard or mouse."
HOMEPAGE = "https://looking-glass.hostfission.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "binutils nettle libsdl2 libsdl2-ttf spice-protocol freeglut fontconfig"

SRC_URI = "git://github.com/gnif/LookingGlass.git;protocol=https;branch=Release/B1 \
           file://0001-Update-new-bfd-API.patch \
"
SRCREV = "163a2e5d0a1168637da2524717b1328165c3c0b0"

inherit cmake pkgconfig

TUNE_CCARGS_append_intel-corei7-64 = " -mno-avx512f"

#Looking-Glass Client Directory
S = "${WORKDIR}/git"
OECMAKE_SOURCEPATH = "${S}/client"
 

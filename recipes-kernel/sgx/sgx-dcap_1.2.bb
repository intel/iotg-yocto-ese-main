SUMMARY = "Intel(R) SGX Data Center Attestation Primitives (DCAP) Out-Of-Tree Driver"
LICENSE = "BSD-3-Clause | GPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/driver/linux/License.txt;md5=633d1f5182ada7cd064194532a4a79d4"

inherit module

SRC_URI = "git://github.com/intel/SGXDataCenterAttestationPrimitives.git \
           file://dcap_intel_sgx_Makefile_for_yocto_build.patch;striplevel=3 \
           file://${PN}-kernel-5.2.patch;striplevel=3 \
           file://${PN}-kernel-5.3.patch;striplevel=3 \
          "

SRCREV = "2c236e7600c579efbfb2e3775b8cb50f0521bda5"
PV_append = "+${SRCPV}"
S = "${WORKDIR}/git/driver/linux"

# fix broken code
KERNEL_CC_append = " -Wno-error=packed-not-aligned"
CLEANBROKEN = "1"

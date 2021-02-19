SUMMARY = "The Intel(R) Graphics Compute Runtime for OpenCL(TM)"
DESCRIPTION = "The Intel(R) Graphics Compute Runtime for OpenCL(TM) \
is an open source project to converge Intel's development efforts \
on OpenCL(TM) compute stacks supporting the GEN graphics hardware \
architecture."


# The developers are not aware of Unix soname conventions
FILES_${PN}-dev = "${includedir}"
ALLOW_EMPTY_${PN}-dev = "1"

#### backport
#commit 4ee7bc0ab408db7388374e86920ffa378b7b4535
#Author: Anuj Mittal <anuj.mittal@intel.com>
#Date:   Fri Oct 23 14:19:09 2020 +0800
#
#    intel-compute-runtime: add libva to DEPENDS
#
#    libva is needed to have cl_intel_va_api_media_sharing [1] extension
#    enabled.
#
#    [1] https://github.com/intel/compute-runtime/blob/master/opencl/doc/cl_intel_va_api_media_sharing.md
#
#    Signed-off-by: Anuj Mittal <anuj.mittal@intel.com>

DEPENDS_append_class-target = " libva"

SUMMARY = "The Intel(R) Graphics Compute Runtime for OpenCL(TM)"
DESCRIPTION = "The Intel(R) Graphics Compute Runtime for OpenCL(TM) \
is an open source project to converge Intel's development efforts \
on OpenCL(TM) compute stacks supporting the GEN graphics hardware \
architecture."


# The developers are not aware of Unix soname conventions
FILES:${PN}-dev = "${includedir}"
ALLOW_EMPTY:${PN}-dev = "1"

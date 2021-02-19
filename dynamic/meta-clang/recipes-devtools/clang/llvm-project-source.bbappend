FILESEXTRAPATHS_prepend_intel-x86-common := "${THISDIR}/files:"

SRC_URI_append_intel-x86-common = " \
                                    file://0001-OpenCL-Fix-support-for-cl_khr_mipmap_image_writes.patch \
                                    file://fix-clang-native-build.patch \
                                    "

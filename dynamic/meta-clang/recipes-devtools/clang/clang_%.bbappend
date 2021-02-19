FILESEXTRAPATHS_prepend_intel-x86-common := "${THISDIR}/files:"

LLVM_TARGETS_TO_BUILD = "X86"

LIBCPLUSPLUS = ""

###### workaround nativesdk broken, finding the wrong python interpreter
# it already uses -DPYTHON_LIBRARY=${STAGING_LIBDIR}/lib${PYTHON_DIR}${PYTHON_ABI}.so -DPYTHON_INCLUDE_DIR=${STAGING_INCDIR}/${PYTHON_DIR}${PYTHON_ABI}
EXTRA_OECMAKE_append_class-nativesdk = " -DPYTHON_EXECUTABLE='${PYTHON}'"

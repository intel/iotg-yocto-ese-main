# On dunfell, autoconf v2.69 does not support configure option "--runstatedir", thus removing it.
EXTRA_OECONF:remove = " --runstatedir=/run"

# fix do_package: QA Issue: tpm2-tss: Files/directories were installed but not shipped in any package.
# The "/var/run" is not packaged with hardknott build, so also not shipping with dunfell.
do_install:append() {
   rm -rf ${D}/var/run
}

# Fix the missing BUILD-ID

python do_compile_prepend() {
    release_fields = d.getVar("OS_RELEASE_FIELDS")
    release_fields += " BUILD_ID"
    d.setVar("OS_RELEASE_FIELDS",release_fields)
}

# To fix the jenkins caching issue , making this to run always
do_compile[nostamp] = "1"

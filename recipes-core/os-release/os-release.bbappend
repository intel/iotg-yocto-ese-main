# Fix the missing BUILD-ID

python do_compile:prepend () {
    release_fields = d.getVar("OS_RELEASE_FIELDS")
    release_fields += " BUILD_ID"
    d.setVar("OS_RELEASE_FIELDS",release_fields)
}

# Fix for jenkins caching issue. making this function to execute always
do_compile[nostamp] = "1"

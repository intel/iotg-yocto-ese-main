def get_libdir_suffix(d):
    import re
    libdir = d.getVar("libdir")
    prefix = d.getVar("prefix")
    return re.sub("^" + prefix + "/lib", "", libdir)


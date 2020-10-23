SRC_URI  = "\
       git://github.com/google/glog.git;nobranch=1 \
       file://0001-Rework-CMake-glog-VERSION-management.patch \
       file://0002-Find-Libunwind-during-configure.patch \
       file://0003-installation-path-fix.patch \
      "
DEPENDS += "gflags"

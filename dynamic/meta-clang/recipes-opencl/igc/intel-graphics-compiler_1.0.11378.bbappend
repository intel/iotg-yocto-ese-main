#Fix the compilation issue due to rebase on https://github.com/intel/vc-intrinsics
SRC_URI:remove = "git://github.com/intel/vc-intrinsics.git;protocol=https;destsuffix=git/vc-intrinsics;name=vc;branch=master"
SRC_URI:append = " git://github.com/intel/vc-intrinsics.git;protocol=https;destsuffix=git/vc-intrinsics;name=vc;nobranch=1"

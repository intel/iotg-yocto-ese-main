SUMMARY = "Inspect and manipulate bpf library"
DESCRIPTION = "bpf is a kernel tool library for inspection and simple manipulation"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

DEPENDS = "elfutils"

do_configure[depends] += "virtual/kernel:do_shared_workdir"

PROVIDES = "virtual/bpf"

inherit kernelsrc

S  = "${WORKDIR}/${BP}"
SPDX_S = "${S}/tools/lib/bpf"

EXTRA_OEMAKE = "V=1 -C ${S}/tools/lib/bpf O=${B} CROSS_COMPILE=${TARGET_PREFIX} CC="${CC}" LD="${LD}" AR=${AR} ARCH=${ARCH} LDFLAGS="${LDFLAGS}""

BPF_SRC ?= "Makefile \
            tools/arch \
            tools/build \
            tools/include \
            tools/scripts \
            tools/perf \
            tools/lib/bpf \
           "

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake DESTDIR=${D} install
}

do_configure[prefuncs] += "copy_bpf_source_from_kernel"
python copy_bpf_source_from_kernel() {
    sources = (d.getVar("BPF_SRC") or "").split()
    src_dir = d.getVar("STAGING_KERNEL_DIR")
    dest_dir = d.getVar("S")
    bb.utils.mkdirhier(dest_dir)
    for s in sources:
        src = oe.path.join(src_dir, s)
        dest = oe.path.join(dest_dir, s)
        if not os.path.exists(src):
            bb.fatal("Path does not exist: %s. Maybe BPF_SRC does not match the kernel version." % src)
        if os.path.isdir(src):
            oe.path.copyhardlinktree(src, dest)
        else:
            bb.utils.copyfile(src, dest)
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

python do_package_prepend() {
    d.setVar('PKGV', d.getVar("KERNEL_VERSION", True).split("-")[0])
}


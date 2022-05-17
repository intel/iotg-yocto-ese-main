# meta-intel-iot-main
This BSP layer provides information on building Yocto Project*-based BSP \
on Intel IoT platforms.

## Table of Contents

    I. Overview
    II. Yocto Project Compatibility
    III. Getting Started
    IV. Setup
        a. Preferred Machine information
        b. Set Preferred Gstreamer / Media versions if any
        c. Set DISTRO_FEATURES if any
        d. Additional and Special Usecases Variables
    V. Supported Hardware
    VI. Dependencies
    VII. Maintenance
    VIII. License

## I. Overview

This is the location of Intel IoTG maintained Yocto Project*-based BSP
For e.g. if a developer need to use the latest development version of \
oe-core or meta-intel source , they have the flexibility here to \
add it and use it

## II. Yocto Project Compatibility

The BSPs contained in this layer are compatible with the Yocto Project \
as per the requirements listed here:

```shell
a. Dunfell
b. Hardknott
```


## III. Getting Started

```shell
git clone git://github.com/openembedded/openembedded-core.git
cd openembedded-core
git clone git://github.com/openembedded/bitbake.git
git clone https://git.yoctoproject.org/git/meta-intel
git clone https://github.com/intel-innersource/os.linux.yocto.build.meta-intel-iot-main.git meta-intel-iot-main

$ . ./oe-init-build-env
```
Add meta-intel and meta-intel-iot-main overlay
```
bitbake-layers add-layer ../meta-intel
bitbake-layers add-layer ../meta-intel-iot-main
```

Check `conf/bblayers.conf` to see that meta-intel and meta-intel-iot-main
is added to layer mix e.g.

```shell
BBLAYERS ?= " \
/path/to/openembedded-core/meta \
/path/to/meta-intel \
/path/to/layer/meta-intel-iot-main \
"
```

## IV. Setup

User / Developer may need to add various configurations mentioned below \
in local.conf based on their feature need.

For eg. this layer will help to use the development version of gstreamer \
by providing the `PREFERRED_VERSION` mentioned below.

### a. Preferred Machine information

```shell
MACHINE = "intel-corei7-64"
```

### b. Set Preferred Gstreamer / Media versions if any

```shell
PREFERRED_VERSION_gstreamer1.0="{version number}%"
e.g.
PREFERRED_VERSION_gstreamer1.0="1.19.%"
```

### c. Set `DISTRO_FEATURES` if any

```shell
DISTRO_FEATURES:append = " opengl systemd"

when systemd added as DISTO_FEATURES please add init_manager

VIRTUAL-RUNTIME_init_manager = "systemd" 
```

### d. Additional and Special Use cases Variables

For ffmpeg its needs commercial license flag

```shell
LICENSE_FLAGS_ACCEPTED += "commercial"
```

## Special Purpose Bitbake Recipes and Classes

### Mender Support Initramfs
mender-initramfs and mender-init are musl and eudev init based
minimalist initramfs environment. These are meant to be built
with:
```shell
TCLIBC = "musl"
PREFERRED_PROVIDER_virtual/kernel = "linux-dummy"
VIRTUAL-RUNTIME_init_manager = "sysvinit"
```

The image assumes the boot disk contains at least 5 partitions,
with the file system and GPT disk label (not to be confused with the
file system label itself) of:

* efi
* primary
* secondary
* data
* swap

The partition labeled `efi` is the EFI System Partition, while `swap`
is a Linux swap partition. Other partitions may be of arbitrary
file system.

See `scripts/lib/wic/canned-wks/mender-io.wks.in` for an example
layout. These partitions may be of any arbitrary size, although primary
and secondary must be of equal size if mender is to be used.

The `data` partition contains the user home directory and the /etc
directory, these are then bind mounted onto position by the initramfs
startup before passing control to the system init daemon (e.g. systemd).
This is done so that configuration modifications and user home contents
persist across mender updates.

See `classes/mender-persistent.bbclass` for details on how files on
the image is post-processed in the end of the do_rootfs task.

It is recommended to use the bitbake multiconfig feature to integrate
these images as dependencies for the main Yocto Linux image.

### mender-reconf

This recipe installs a template of the mender configuration file used by
mender-init to reconfigure mender disk paths as they are found during the
Linux startup process.

### mender-relabel

This recipe installs a script that relabels a file system after a mender
update so the mender-init startup process can find it upon the next
reboot.

### ese-image-features.bbclass and secure boot

This class is primarily used to support secure boot for Yocto Linux BSPs.
While meta-intel also provides secure boot support via systemd-boot by
signing the boot loader, kernel and initramfs as a single EFI PE executable,
the approach taken by ese-image-features is done with shim and grub. This
approach is done to support mender grub environment settings to switch
between primary and secondary partitions.

The boot process diagram is similar to:
https://access.redhat.com/articles/5254641

The shim executable is signed by the Database (DB) key, which itself
contains the Machine Owner Key (MOK). Grub and the kernel is signed with
the MOK keys.

Another caveat for favoring grub over systemd-boot is that most UEFI
implementations do not provide a Simple File System protocol for
file systems such as EXT4 typically used by Linux root firestorms. To
ensure mender updates on the BSP kernel and its modules are performed
atomically, it is necessary that the kernel and its modules are stored
on the same partition.

The PEM formatted keys used in the Yocto Linux image build process are
available in ${TMPDIR}/deploy/images/${MACHINE}/secure-boot-certificates
after the build process completes.

## V. Supported Hardware

The following boards / platforms undergo regular basic testing.
1. CML-S RVP
2. EHL RVP
3. TGL-U & TGL-H RVP
4. ADL-S RVP / CRB
5. ICL-D RVP
6. KMB


## VI. Dependencies

This layer depends on:

```shell
URI: git://github.com/openembedded/openembedded-core.git
branch: hardknott
revision: HEAD

URI: git://github.com/openembedded/bitbake.git
branch: 1.50
revision: HEAD

URI: https://git.yoctoproject.org/git/meta-intel
branch: master
revision: HEAD
```

## VII. Maintenance

Maintainers:

1. Yong, Jonathan <jonathan.yong@intel.com>

## VIII. License

All metadata is MIT licensed unless otherwise stated. Source code included \
in tree for individual recipes is under the LICENSE stated in each recipe \
(.bb file) unless otherwise stated.

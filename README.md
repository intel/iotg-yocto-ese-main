# meta-intel-ese-main
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
a. Zeus
b. Dunfell
```


## III. Getting Started

```shell
git clone git://github.com/openembedded/openembedded-core.git
cd openembedded-core
git clone git://github.com/openembedded/bitbake.git
git clone https://git.yoctoproject.org/git/meta-intel
git clone https://gitlab.devtools.intel.com/OWR/IoTG/ESE/Linux-Integration/Yocto/meta-intel-ese-main-pre.git meta-intel-ese-main

$ . ./oe-init-build-env
```
Add meta-intel and meta-intel-ese-main overlay
```
bitbake-layers add-layer ../meta-intel
bitbake-layers add-layer ../meta-intel-ese-main
```

Check `conf/bblayers.conf` to see that meta-intel and meta-intel-ese-main
is added to layer mix e.g.

```shell
BBLAYERS ?= " \
/path/to/openembedded-core/meta \
/path/to/meta-intel \
/path/to/layer/meta-intel-ese-main \
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
PREFERRED_VERSION_gstreamer1.0="1.17.%"
```

### c. Set `DISTRO_FEATURES` if any

```shell
DISTRO_FEATURES_append = " opengl systemd"
```

### d. Additional and Special Usecases Variables

For ffmpeg its needs commercial license flag

```shell
LICENSE_FLAGS_WHITELIST += "commercial"
```

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
branch: dunfell
revision: HEAD

URI: git://github.com/openembedded/bitbake.git
branch: 1.46
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

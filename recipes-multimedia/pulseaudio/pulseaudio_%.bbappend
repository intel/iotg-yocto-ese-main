# workaround for consolekit functionality absorbed by systemd, implies x11 dependency
# workaround already in Yocto 2.8
# https://git.yoctoproject.org/cgit/cgit.cgi/poky/commit/?id=84c1ca18f0e1fe278ad7744fe4fe8d7860a2e944
RDEPENDS:pulseaudio-server:remove = "pulseaudio-module-console-kit"
RDEPENDS:pulseaudio-server:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', \
    bb.utils.contains('DISTRO_FEATURES', 'systemd', 'pulseaudio-module-systemd-login', 'pulseaudio-module-console-kit', d), \
    '', d)}"

RDEPENDS:pulseaudio-server:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'pulseaudio-module-loopback', '', d)}"

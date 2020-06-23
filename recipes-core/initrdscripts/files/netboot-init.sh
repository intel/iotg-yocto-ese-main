#! /bin/sh
mkdir -p /dev /proc /sys /run/udev /tmp

mount -t devtmpfs devtmpfs /dev  -o nosuid
mount -t proc     proc     /proc -o nosuid,nodev,noexec
mount -t sysfs    sysfs    /sys  -o nosuid,nodev,noexec

# try mount efivarfs
if [ -d /sys/firmware/efi/efivars ]; then
    mount -o nosuid,nodev,noexec -t efivarfs efivarfs /sys/firmware/efi/efivars
fi

# busybox findfs cannot handle GPT PARTUUIDs!
udevd --daemon
udevadm trigger
udevadm settle --timeout=3


if [ -c /dev/kmsg ]; then
    use_kmsg=1
fi

say() {
    if [ -n "${use_kmsg}" ]; then
        echo "[initrd] $@" > /dev/kmsg
    else
        echo "[initrd] $@" 1>&2
    fi
}

get_cmd(){
    value="$(grep \\W${1}=\\S\\+ -o < /proc/cmdline | cut -d= -f2- )"
    if [ x"${value}" = "x" ]; then
        echo "$2"
    else
        echo "${value}"
    fi
}

say "Found netmount script"
if [ -e /etc/init.d/netmount ]; then
    . /etc/init.d/netmount
fi

init="$(get_cmd init /sbin/init)"

# netmount script is expected to put root in /tmp/root
udevadm control --exit
if [ -d /tmp/root -a -e "/tmp/root/${init}" ]; then
    exec switch_root /tmp/root "${init}"
else
    exec /bin/sh
fi

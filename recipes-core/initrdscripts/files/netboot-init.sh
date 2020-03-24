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

if [ -e /etc/init.d/netmount ]; then
    /etc/init.d/netmount
fi

# netmount script is expected to put root in /tmp/root
if [ -d /tmp/root -a -e /tmp/root/sbin/init ]; then
    udevadm control --exit
    exec switch_root /tmp/root /sbin/init
else
    exec /bin/sh
fi

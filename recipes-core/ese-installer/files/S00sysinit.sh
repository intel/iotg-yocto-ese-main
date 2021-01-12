#! /bin/sh
mkdir -m 0755 -p /dev /proc /sys /run/udev /tmp

mount -t devtmpfs devtmpfs /dev  -o nosuid
mount -t proc     proc     /proc -o nosuid,nodev,noexec
mount -t sysfs    sysfs    /sys  -o nosuid,nodev,noexec

# try mount pts
mkdir -m 0755 /dev/pts
mount -o rw,nosuid,noexec,relatime,gid=5,mode=620,ptmxmode=000 -t devpts devpts /dev/pts

# try mount efivarfs
if [ -d /sys/firmware/efi/efivars ]; then
  mount -o nosuid,nodev,noexec -t efivarfs efivarfs /sys/firmware/efi/efivars
fi

#! /bin/sh
# get ip address
udhcpc -n

# prepare an overlayfs for read-only FS, can mount a real partition to save changes
mount -t tmpfs tmpfs /tmp
mkdir -p /tmp/root /tmp/root_ro /tmp/work /tmp/upper

# call iscsistart, we assume the server is at 192.168.1.1, use and install dig if needed to resolve the address
iscsistart -i iqn.2005-03.org.open-iscsi:3b5f3d51247f -t iqn.2005-03.org.open-iscsi:ae8e658ea7e1 -g 1 -a 192.168.1.1 -p 3260 -u yocto -w yocto

# assume udevadm start/trigger is running, mount the raw example image
remote='/dev/disk/by-path/ip-192.168.1.1:3260-iscsi-iqn.2005-03.org.open-iscsi:ae8e658ea7e1-lun-0'

echo "waiting for ${remote}"
while [ ! -b "${remote}" ]; do
  usleep 100
done

# busybox mount is not able to handle the disk path directly
disk="$(realpath '/dev/disk/by-path/ip-192.168.1.1:3260-iscsi-iqn.2005-03.org.open-iscsi:ae8e658ea7e1-lun-0')"
mount -o ro "${disk}" /tmp/root_ro

# mount an overlay
mount -t overlay overlay -o upperdir=/tmp/upper,workdir=/tmp/work,lowerdir=/tmp/root_ro /tmp/root

# transfer mount
mount --rbind /dev /tmp/root/dev
mount --rbind /proc /tmp/root/proc
mount --rbind /sys /tmp/root/sys

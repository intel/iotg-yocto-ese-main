#! /bin/sh
iface="$(get_cmd netmount.iface eth0)"
say "Using ${iface} for network boot"

# get ip address for eth0
udhcpc -n -f -q -t 30 -i "${iface}"

# prepare an overlayfs for read-only FS, can mount a real partition to save changes
mount -t tmpfs tmpfs /tmp
mkdir -p /tmp/root /tmp/root_ro /tmp/work /tmp/upper

initiator="$(get_cmd netmount.initiator iqn.2005-03.org.open-iscsi:3b5f3d51247f)"
target="$(get_cmd netmount.target iqn.2005-03.org.open-iscsi:ae8e658ea7e1)"
target_ip="$(get_cmd netmount.target_ip 192.168.1.1)"
target_port="$(get_cmd netmount.target_port 3260)"
target_pt="$(get_cmd netmount.target_portal 1)"

mkdir -p /etc/iscsi /var/run
echo "${initiator}" > /etc/iscsi/initiatorname.iscsi

# call iscsistart, we assume the server is at 192.168.1.1, use and install dig if needed to resolve the address
iscsistart -i "${initiator}" -t "${target}" -g "${target_pt}" -a "${target_ip}" -p "${target_port}" -u yocto -w yocto
while [ "${?}" -ne 0 ]; do
  sleep 5
  iscsiadm -m session --logout
  killall -KILL iscsid
  sleep 5
  iscsistart -i "${initiator}" -t "${target}" -g "${target_pt}" -a "${target_ip}" -p "${target_port}" -u yocto -w yocto
done

# assume udevadm start/trigger is running, mount the raw example image
remote="/dev/disk/by-path/ip-${target_ip}:3260-iscsi-${target}-lun-0"

# waiting for enumeration
say "waiting for ${remote}"
while [ ! -b "${remote}" ]; do
  usleep 100
done

# busybox mount is not able to handle the disk path directly
disk="$(realpath ${remote})"
mount -o ro "${disk}" /tmp/root_ro

# mount an overlay
mount -t overlay overlay -o rw,upperdir=/tmp/upper,workdir=/tmp/work,lowerdir=/tmp/root_ro /tmp/root

# transfer mount
mount --rbind /dev /tmp/root/dev
mount --rbind /proc /tmp/root/proc
mount --rbind /sys /tmp/root/sys

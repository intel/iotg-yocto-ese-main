#! /bin/sh
mkdir -p /dev /proc /sys /run/udev

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

fail() {
    say "Startup failed"
    exec /bin/sh
}

find_part_nofatal=""
find_part() {
    name="$1"
    hint="$(grep \\W${name}=\\S\\+ -o < /proc/cmdline | cut -d= -f2- )"
    if [ -z "find_part_nofatal" -a -z "${hint}" ]; then
        say "Cannot find ${name}"
        fail
    fi

    type="$( echo "${hint}" | cut -d= -f1 )"
    value="$( echo "${hint}" | cut -d= -f2 )"

    if [ "${type}" == "LABEL" ]; then
        echo "/dev/disk/by-label/${value}"
        return
    fi

    if [ "${type}" == "PARTLABEL" ]; then
        echo "/dev/disk/by-partlabel/${value}"
        return
    fi

    if [ "${type}" == "PARTUUID" ]; then
        echo "/dev/disk/by-partuuid/${value}"
        return
    fi

    if [ "${type}" == "UUID" ]; then
        echo "/dev/disk/by-uuid/${value}"
        return
    fi

    if [ "${type}" == "ID" ]; then
        echo "/dev/disk/by-id/${value}"
        return
    fi

    # unknown?
    echo "${hint}"
}

wait_file() {
    source="$1"
    # path-like? wait for it to enumerate
    if $( echo "${source}" | grep -q ^/dev ) ; then
       say "waiting for ${source}"
       while [ ! -b "${source}" ]; do
           usleep 100
       done
    fi
}

check_mount() {
    source="$1"
    wait_file "$1"
    # some weirdo path leaks breaking fwupdate?
    realpart="$( realpath ${source} )"
    shift
    mount "${realpart}" $@
    if [ "$?" -ne 0 ]; then
      say "Fatal error mounting ${source}"
      fail
    fi
}

# default init program
init_bin="/sbin/init"
# check if user wants a custom init
update_init(){
    init="$(grep \\Winit=\\S\\+ -o < /proc/cmdline | cut -d= -f2- )"
    if [ -n "${init}" ]; then
        init_bin="${init}"
        say "Using init ${init}"
    fi
}

# are we resuming?
find_part_nofatal=1
resume_part="$( find_part resume )"
if [ -n "${resume_part}" ]; then
    wait_file "${resume_part}"
    real_resume="$( realpath ${resume_part} )"
    say "trying to resume using ${resume_part} (${real_resume})"
    echo "${real_resume}" > /sys/power/resume
    say "resume failed, continuing with normal boot path"
fi
find_part_nofatal=""

# real root, mount as rw, as we need to update the mender.conf file
realroot="/realroot"
rootpart="$( find_part root )"
mkdir -p "${realroot}"
check_mount "${rootpart}" "${realroot}" -o rw || fail
say "root done"

# mount data partition
data_part="$( find_part mender.data )"
check_mount "${data_part}" "${realroot}/data" -o rw || fail
say "data done"

# mount boot partition, must be rw so mender can update grub env
efi_part="$( find_part mender.efi )"
if [ -n "${efi_part}" ]; then
  check_mount "${efi_part}" "${realroot}/boot/efi" -o rw || fail
  say "efi done"
fi

# try swap
swap_part="$( find_part mender.swap )"
if [ -n "${swap_part}" ]; then
  swapon "${swap_part}"
  say "swap done"
fi

# fix up root
mount -o rbind /dev  "${realroot}/dev"  || fail
mount -o rbind /proc "${realroot}/proc" || fail
mount -o rbind /sys  "${realroot}/sys"  || fail
say "rbind done"

# fix up persistent content
mount -o rbind "${realroot}/data/persistent/etc"  "${realroot}/etc"  || fail
mount -o rbind "${realroot}/data/persistent/var"  "${realroot}/var"  || fail
mount -o rbind "${realroot}/data/persistent/home" "${realroot}/home" || fail
say "rbind persistent done"

# fix up apparmor
mount -o rbind "${realroot}/usr/share/intel/managed/apparmor"   "${realroot}/etc/apparmor"   || fail
mount -o rbind "${realroot}/usr/share/intel/managed/apparmor.d" "${realroot}/etc/apparmor.d" || fail
mount -o rbind "${realroot}/data/persistent/apparmor.d.cache"   "${realroot}/etc/apparmor.d/cache" || fail
say "rbind apparmor done"

# fix up mender pointers
primary_part="$( realpath $( find_part mender.primary ) )"
secondary_part="$( realpath $( find_part mender.secondary ) )"

if [ ! -f "${realroot}/etc/mender/mender.conf" -a -f "${realroot}/etc/mender/mender.conf.in" ]; then
    if [ -z "$primary_part" -o -z "$secondary_part" ]; then
        fail
    fi
    sed -e "s@mender.primary@${primary_part}@g" -e "s@mender.secondary@${secondary_part}@g" < "${realroot}/etc/mender/mender.conf.in" > "${realroot}/etc/mender/mender.conf"
fi
say "mender.conf done"

say "waiting for udev"
udevadm settle --timeout=3
udevadm control --exit --timeout=3
say "udev done, transition to real rootfs"

update_init
exec switch_root -c /dev/console "${realroot}" "${init_bin}"
fail

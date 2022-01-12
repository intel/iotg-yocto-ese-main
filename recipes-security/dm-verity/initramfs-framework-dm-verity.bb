# This recipe creates a module for the initramfs-framework
# which activates the partition identified via the "root"
# kernel parameter using the dm-verity hashes stored in
# the partition identified as the next consecutive
# parition after root partition and changes bootparam_root
# so that the init code uses the read-only,
# integrity protected mapped partition.

# Reference:
# https://github.com/intel/intel-iot-refkit/blob/master/meta-refkit-core/recipes-images/images/initramfs-framework-refkit-dm-verity.bb

SUMMARY = "dm-verity module for the modular initramfs system"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

require boot-settings.inc

dmverity_script() {
    dmverity_enabled() {
        debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): Entered !"

        # Check all kernel parameters.
        debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): verity = $bootparam_verity"
        debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): roothash = $bootparam_roothash"
        debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): root = $bootparam_root"

        # Check if veritysetup is installed by printing its version.
        debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): veritysetup version = $(veritysetup --version)"

        # Check if dm-verity is enabled, from the kernel parameters.
        if [ "$bootparam_verity" -eq 1 ]; then
            debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): verity is equal to 1."
            if [ "$bootparam_root" ]; then
                debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): root found."
                if [ -n "$bootparam_roothash" ]; then
                    debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): roothash is non-zero."
                    return 0
                else
                    debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): roothash is NOT non-zero !"
                    return 1
                fi
            else
                debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): root NOT found !"
                return 1
            fi
        else
            debug "initramfs-framework-dm-verity.bb: dmverity_enabled(): verity is NOT equal to 1 !"
            return 1
        fi
    }

    dmverity_run()
    {
        debug "initramfs-framework-dm-verity.bb: dmverity_run(): Entered !"

        # Check if hash device exists.
        # ASSUMPTION: Hash partition number ('X' in /dev/sdX) is the next successive integer from the root device.
        rootdev_num=$(echo -n $bootparam_root | tail -c 1)
        hashdev_num=$(expr ${rootdev_num} + 1)
        hash_partition="$(echo "${bootparam_root%?}$hashdev_num")"
        debug "initramfs-framework-dm-verity.bb: dmverity_run(): hash_partition = $hash_partition"

        C=0
        delay=${bootparam_rootdelay:-1}
        timeout=${bootparam_roottimeout:-5}

        # Loop to delay in case partitions take time to appear. Hence, veritysetup command
        # could potentially fail because of a temporary error condition ("devices not ready yet").
        while true; do
            seconds=$( expr "$C" '*' "$delay" )
            # shellcheck disable=SC2035
            if [ "$seconds" -gt "$timeout" ]; then
                fatal "root partition $bootparam_root and dm-verity hash partition $hash_partition not found."
            fi

            debug "initramfs-framework-dm-verity.bb: dmverity_run(): Check if dm-verity magic number 'verity' exists on the hash partition $hash_partition:"
            debug "$(hexdump -C $hash_partition | head)"

            if veritysetup create "${DEVICE_MAPPER_ROOTFS_NAME}" "$bootparam_root" "$hash_partition" "$bootparam_roothash"; then
                bootparam_root="/dev/mapper/${DEVICE_MAPPER_ROOTFS_NAME}"
                debug "initramfs-framework-dm-verity.bb: dmverity_run(): After veritysetup create, root = $bootparam_root"
                debug "initramfs-framework-dm-verity.bb: dmverity_run(): After veritysetup create, ls -halt /dev/mapper/ = $(ls -halt /dev/mapper/)"
                return
            fi

            debug "Sleeping for $delay second(s) to wait for root to settle..."
            sleep "$delay"
            C=$( expr $C + 1 )
        done
    }
}

python do_install () {
    import os
    import subprocess

    os.makedirs(os.path.join(d.getVar('D'), 'init.d'))
    with open(os.path.join(d.getVar('D'), 'init.d', '80-dmverity'), 'w') as f:
        f.write(d.getVar('dmverity_script'))
}

FILES:${PN} = "/init.d /etc"
RDEPENDS:${PN} += " \
    initramfs-framework-base \
    cryptsetup \
"

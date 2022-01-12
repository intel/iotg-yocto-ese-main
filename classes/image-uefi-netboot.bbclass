# UEFI PXE boot packager, based on image-live.bbclass
# use with IMGCLASSES +=  "image-uefi-netboot" and IMAGE_FSTYPES = "uefi-netboot.tar.bz2"
# Grub with fedora/debian patches disable module loading when secureboot is enabled
# grub can optionally be signed with gpg to ensure bzimage/initramfs is secure, keep in mind that grub is GPLv3+
inherit gnu-efi image-uefi-netboot-paths
require conf/image-uefi.conf

EFI_PROVIDER ?= "grub-efi"
do_bootimg[depends] += "tar-native:do_populate_sysroot virtual/kernel:do_deploy ${PN}:do_image_${@d.getVar('LIVE_ROOTFS_TYPE').replace('-', '_')}"

IMAGE_TYPES_MASKED += "uefi-netboot"
UEFI_NETBOOT_DIR = "${B}/uefi-netboot"
UEFI_NETBOOT_SECURE_BOOT_SIGN ?= "0"
INITRAMFS_IMAGE_PATH ?= "${DEPLOY_DIR_IMAGE}/${INITRAMFS_IMAGE_NAME}-${MACHINE}.cpio"
UEFI_NETBOOT_COPY_FS = ""
UEFI_NETBOOT_GRUB_GPG_SIGN ?= "0"
UEFI_NETBOOT_GRUB_MODULES ?= "normal tftp linux gcry_sha256 gcry_dsa gcry_rsa gcry_sha512"
GRUB_GPG_HOME = "${B}/gpghome"
GPG_SOCKET = ""

python(){
  import re

  image_types = d.getVar('IMAGE_FSTYPES')
  pn = d.getVar('PN')
  for image_type in re.split(r'\s+',image_types):
    # re.match(r'^tar(\..+)*$', image_type)
    if image_type and not re.match('^uefi-netboot', image_type):
      d.appendVarFlag('do_bootimg', 'depends', ' ' + pn + ':do_image_' + image_type.replace('-', '_'))
      d.appendVar('UEFI_NETBOOT_COPY_FS', ' %s' % image_type)

  #### UEFI bits
  def add_grub_deps(d):
    d.appendVarFlag('do_bootimg', 'depends', ' grub-native:do_populate_sysroot grub:do_populate_sysroot grub-efi:do_populate_sysroot virtual/grub-bootconf:do_populate_sysroot')

  # enable signing?
  sign = d.getVar('UEFI_NETBOOT_SECURE_BOOT_SIGN')
  if sign != '0':
    d.appendVarFlag('do_bootimg', 'depends', ' sbsigntool-native:do_populate_sysroot')

  # gpg sign
  gpg = d.getVar('UEFI_NETBOOT_GRUB_GPG_SIGN')
  if gpg != '0':
    d.appendVarFlag('do_bootimg', 'depends', ' gnupg-native:do_populate_sysroot')

  # do we want secure boot? Only shim + grub supported at the moment
  efi_provider = d.getVar('EFI_PROVIDER')
  if efi_provider == 'shim':
    d.appendVarFlag('do_bootimg', 'depends', ' shim:do_deploy virtual/secure-boot-certificates:do_deploy')
    shim_loader = d.getVar('SHIM_LOADER_PROVIDER')
    if shim_loader == 'grub-efi':
      add_grub_deps(d)
    else:
      bb.error('Unsupported SHIM_LOADER_PROVIDER type!')
  elif efi_provider == 'grub-efi':
    add_grub_deps(d)
  else:
    bb.error('Unsupported EFI_PROVIDER %s!' % efi_provider)

  ### initramfs stuff
  initrd = d.getVar('INITRAMFS_IMAGE_RECIPE')
  if initrd:
    if re.match("^(mc|multiconfig):", initrd):
      flag = 'mcdepends'
    else:
      flag = 'depends'
    d.appendVarFlag('do_bootimg', flag, ' %s:do_image_complete' % initrd)
}

GRUB_LIBDIR = "INVALID"
GRUB_LIBDIR:x86-64 = "x86_64-efi"
GRUB_LIBDIR:x86 = "i386-efi"

uefi_netboot_do_sign() {
	sbsign --key "$1" --cert "$2" --output "$4" "$3"
}

###
# insmod command is disabled if secureboot enabled
uefi_netboot_build(){
	rm -rf "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
	install -m 755 -d "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
	# Unix socket path names cannot exceed 108 characters, workaround with a symlink
	gpg_root="$(mktemp -d)"
	gpg_home="${gpg_root}/link"
	ln -s "${GRUB_GPG_HOME}" "${gpg_home}"

	if [ "${UEFI_NETBOOT_GRUB_GPG_SIGN}" -ne 0 ]; then
		gpg --no-permission-warning --batch --yes --disable-dirmngr --homedir "${gpg_home}" --passphrase '' --quick-generate-key grub-signature rsa4096
		gpg --no-permission-warning --batch --yes --disable-dirmngr --homedir "${gpg_home}" --passphrase '' --export -o ${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/grub.pub grub-signature
	fi

	if [ "${UEFI_NETBOOT_SECURE_BOOT_SIGN}" -ne "0" ]; then
		uefi_netboot_do_sign ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.key \
		${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.crt \
		"${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}" \
		"${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/${KERNEL_IMAGETYPE}"
	else
		install -m 644 "${DEPLOY_DIR_IMAGE}/${KERNEL_IMAGETYPE}" "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
	fi

	if [ -n "${INITRAMFS_IMAGE_RECIPE}" ]; then
		install -m 644 "${INITRAMFS_IMAGE_PATH}" "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
	fi

	if [ "${UEFI_NETBOOT_GRUB_GPG_SIGN}" -ne 0 ]; then
		grub-mknetdir --net-directory="${UEFI_NETBOOT_DIR}" --subdir="/${UEFI_NETBOOT_TFTPROOT}" --directory="${STAGING_LIBDIR}/grub/${GRUB_LIBDIR}" \
		--modules="${UEFI_NETBOOT_GRUB_MODULES}" -k ${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/grub.pub
	else
		grub-mknetdir --net-directory="${UEFI_NETBOOT_DIR}" --subdir="/${UEFI_NETBOOT_TFTPROOT}" --directory="${STAGING_LIBDIR}/grub/${GRUB_LIBDIR}" \
		--modules="${UEFI_NETBOOT_GRUB_MODULES}"
	fi

	if [ "${SHIM_LOADER_PROVIDER}" = "grub-efi" -o "${EFI_PROVIDER}" = "grub-efi" ]; then
		install -m 644 "${STAGING_DIR_HOST}/${EFI_FILES_PATH}/grub.cfg" "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
		if [ "${UEFI_NETBOOT_SECURE_BOOT_SIGN}" -ne 0 ]; then
			uefi_netboot_do_sign ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.key \
				${DEPLOY_DIR_IMAGE}/secure-boot-certificates/yocto.crt \
				${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/${GRUB_LIBDIR}/core.efi \
				${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/${GRUB_LIBDIR}/core.efi.signed
			mv ${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/${GRUB_LIBDIR}/core.efi.signed \
				${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/${GRUB_LIBDIR}/core.efi
		fi
	fi

	# gpg sign for grub
	if [ "${UEFI_NETBOOT_GRUB_GPG_SIGN}" -ne 0 ]; then
		find "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}" -type f \
			-execdir \
			gpg --no-permission-warning --batch --yes \
			--disable-dirmngr --homedir "${gpg_home}" --passphrase '' --detach-sign -u grub-signature '{}' ';'
		rm -rf "${gpg_root}"		
	fi

	if [ "${EFI_PROVIDER}" = "shim" ]; then
		if [ "${UEFI_NETBOOT_SECURE_BOOT_SIGN}" -ne "0" ]; then
			uefi_netboot_do_sign ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.key \
				${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.crt \
				"${DEPLOY_DIR_IMAGE}/shim/mm${GNU_EFI_ARCH}.efi" \
				"${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/mm${GNU_EFI_ARCH}.efi"

			uefi_netboot_do_sign ${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.key \
				${DEPLOY_DIR_IMAGE}/secure-boot-certificates/db.crt \
				"${DEPLOY_DIR_IMAGE}/shim/shim${GNU_EFI_ARCH}.efi" \
				"${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}/shim${GNU_EFI_ARCH}.efi"
		else
			install -m 644 "${DEPLOY_DIR_IMAGE}/shim/mm${GNU_EFI_ARCH}.efi" "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
			install -m 644 "${DEPLOY_DIR_IMAGE}/shim/shim${GNU_EFI_ARCH}.efi" "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
		fi
	fi

	for fs in ${UEFI_NETBOOT_COPY_FS}; do
		install -m 644 ${IMGDEPLOYDIR}/${IMAGE_BASENAME}-${MACHINE}.${fs} "${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
	done
}

python fakeroot do_bootimg() {
  bb.build.exec_func('uefi_netboot_build', d)
}
do_bootimg[subimages] = "uefi-netboot"
do_bootimg[imgsuffix] = ".uefi-netboot."
do_bootimg[fakeroot] = "1"
do_bootimg[cleandirs] += "${GRUB_GPG_HOME} ${UEFI_NETBOOT_DIR}/${UEFI_NETBOOT_TFTPROOT}"
addtask bootimg before do_image_complete
do_image_uefi_netboot_tar[depends] += "${PN}:do_bootimg"
IMAGE_CMD:uefi-netboot.tar = "${IMAGE_CMD_TAR} --numeric-owner -cf ${IMGDEPLOYDIR}/${IMAGE_NAME}${IMAGE_NAME_SUFFIX}.uefi-netboot.tar -C ${UEFI_NETBOOT_DIR} . || [ $? -eq 1 ]"

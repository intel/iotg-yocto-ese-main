# should be in lowercase for filename comparison despite FAT being case insensitive
ESE_UEFI_SIGNING_EARLY ??= "shim${GNU_EFI_ARCH}.efi boot${GNU_EFI_ARCH}.efi fwupd${GNU_EFI_ARCH}.efi"
ESE_UEFI_SIGNING_SHIM ??= "mm${GNU_EFI_ARCH}.efi"
ESE_UEFI_SIGNING_EXCLUDE ??= ""

do_ese_copy_fwupd(){
        install -m644 "${DEPLOY_DIR_IMAGE}/fwupd-efi/fwupd${GNU_EFI_ARCH}.efi" "${IMAGE_ROOTFS}/${EFI_FILES_PATH}"
}

python do_ese_boot_sign(){
  import os
  import re
  import shutil
  import subprocess

  def sign_pe(d, file, crt, key):
    output = file + '.signed'
    subprocess.run(args=['sbsign', '--key', key, '--cert', crt, '--output', output, file], check=True)
    os.remove(file)
    shutil.move(output, file)

  def sign_pe_db(d, file):
    crt = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/db.crt')
    key = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/db.key')
    sign_pe(d, file, crt, key)

  def sign_pe_shim(d, file):
    crt = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/shim.crt')
    key = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/shim.key')
    sign_pe(d, file, crt, key)

  def sign_pe_mok(d, file):
    crt = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/yocto.crt')
    key = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/yocto.key')
    sign_pe(d, file, crt, key)

  # libsign stub
  def sign_file(d, file):
    return

  def sign_nop(d, file):
    return

  # todo: Other arch support?
  def is_PE(magic):
    return '(EFI application)' in magic

  def file_magic(d, file):
    # Older verison of file installed on HOSTOOLS i.e. build host machine may not detect EFI binaries correctly
    # Use more recent version of file command from RECIPE_SYSROOT_NATIVE
    file_env = os.environ.copy()
    file_native_path = os.path.join(d.getVar('RECIPE_SYSROOT_NATIVE'), 'usr/bin/file-native')
    file_env["PATH"] = file_native_path + ":" + file_env["PATH"]
    p = subprocess.run(args=['file', '-bk', file], stdout=subprocess.PIPE, check=True, universal_newlines=True, env=file_env)
    return p.stdout.strip()

  def do_signing_task(d, workqueue):
    for filepath in workqueue.keys():
      work = workqueue[filepath]
      if work is not None:
        work(d, filepath)

  def get_signing_method(d, file):
    suffix = d.getVar('GNU_EFI_ARCH')
    magic = file_magic(d, file)
    early = set(d.getVar('ESE_UEFI_SIGNING_EARLY').split())
    shim = set(d.getVar('ESE_UEFI_SIGNING_SHIM').split())

    if is_PE(magic):
      # warn if already signed
      sig = get_signature(filepath)
      if len(sig):
        bb.warn('File "%s" is already signed!:\n%s' % (filepath, sig))
      if os.path.basename(file).lower() in shim:
        return sign_pe_shim
      if os.path.basename(file).lower() in early:
        return sign_pe_db
      else:
        return sign_pe_mok
    return sign_file

  def get_signature(file):
    cmd = subprocess.run(args=['sbverify', '--list', file], stdout=subprocess.PIPE, check=True, universal_newlines=True)
    return cmd.stdout.strip()

  ## walk through /boot area
  deploydir = d.getVar('DEPLOY_DIR_IMAGE')
  rootfs = d.getVar('IMAGE_ROOTFS')
  exclude = set(d.getVar('ESE_UEFI_SIGNING_EXCLUDE').split())

  tasks = {}
  for r, dirs, fl in os.walk(os.path.join(rootfs, 'boot')):
    if len(fl) > 0:
      for f in fl:
        filepath = os.path.join(r, f)
        # skip if a symlink
        if os.path.islink(filepath):
          continue
        # skip excluded files
        if os.path.basename(filepath).lower() in exclude:
          continue
        tasks[filepath] = get_signing_method(d, filepath)
  do_signing_task(d, tasks)
}

do_rootfs[depends]     += "virtual/secure-boot-certificates:do_deploy file-native:do_populate_sysroot sbsigntool-native:do_populate_sysroot fwupd-efi:do_deploy"
do_rootfs[recideptask] += "virtual/secure-boot-certificates:do_deploy"
python(){
    d.appendVar('ESE_IMAGE_CALLS', ' do_ese_copy_fwupd;')
    d.appendVar('ESE_IMAGE_CALLS', ' do_ese_boot_sign;')
}

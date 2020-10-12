# should be in lowercase for filename comparison despite FAT being case insensitive
ESE_UEFI_SIGNING_EARLY ??= "shim${GNU_EFI_ARCH}.efi boot${GNU_EFI_ARCH}.efi fwup${GNU_EFI_ARCH}.efi"
ESE_UEFI_SIGNING_EXCLUDE ??= "mm${GNU_EFI_ARCH}.efi"

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

  def sign_pe_mok(d, file):
    crt = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/shim.crt')
    key = os.path.join(d.getVar('DEPLOY_DIR_IMAGE'), 'secure-boot-certificates/shim.key')
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
    p = subprocess.run(args=['file', '-bk', file], stdout=subprocess.PIPE, check=True, universal_newlines=True)
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

    if is_PE(magic):
      # warn if already signed
      sig = get_signature(filepath)
      if len(sig):
        bb.warn('File "%s" is already signed!:\n%s' % (filepath, sig))
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

do_image[depends] += "virtual/secure-boot-certificates:do_deploy file-native:do_populate_sysroot"
python(){
    d.appendVar('IMAGE_PREPROCESS_COMMAND', ' do_ese_boot_sign;')
}

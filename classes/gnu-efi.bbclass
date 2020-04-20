GNU_EFI_ARCH ?= ""
GNU_EFI_ARCH_x86-64 = "x64"
GNU_EFI_ARCH_x86 = "ia32"
GNU_EFI_ARCH_arm = "arm"
GNU_EFI_ARCH_aarch64 = "aa64"

python() {
  arch = d.getVar('GNU_EFI_ARCH')
  if not (arch and len(arch) > 0):
    bb.error('Unknown GNU_EFI_ARCH platform!')
}

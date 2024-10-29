inherit gnu-efi deploy

do_deploy(){
  install -m 755 -d ${DEPLOYDIR}/fwupd-efi
  install -m 644 ${WORKDIR}/build/efi/fwupd${GNU_EFI_ARCH}.efi ${DEPLOYDIR}/fwupd-efi
}

addtask do_deploy before do_build after do_install

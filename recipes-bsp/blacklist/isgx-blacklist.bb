DESCRIPTION = "Locked/Unlocked SGX drivers cannot be both loaded at the same time"
LICENSE = "MIT"

inherit allarch
do_install(){
	mkdir -p ${D}/etc/modprobe.d
	echo 'blacklist isgx' > ${D}/etc/modprobe.d/sgx.conf
	echo 'blacklist sgx' >> ${D}/etc/modprobe.d/sgx.conf
	echo 'blacklist intel_sgx' >> ${D}/etc/modprobe.d/sgx.conf
}

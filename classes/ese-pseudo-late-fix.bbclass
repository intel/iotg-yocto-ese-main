fakeroot python do_ese_image_fixup(){
  from oe.utils import execute_pre_post_process
  cb = d.getVar("ESE_IMAGE_CALLS")
  execute_pre_post_process(d, cb)
}

addtask do_ese_image_fixup before do_flush_pseudodb after do_rootfs
do_ese_image_fixup[depends] = "${ESE_IMAGE_FIXUP_DEPS}"

ESE_IMAGE_FIXUP_DEPS ??= ""

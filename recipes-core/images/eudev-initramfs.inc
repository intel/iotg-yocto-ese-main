# for eudev
inherit extrausers
EXTRA_USERS_PARAMS = "\
  groupadd -g 5 tty; \
  groupadd -g 6 disk; \
  groupadd -g 7 lp; \
  groupadd -g 9 kmem; \
  groupadd -g 18 audio; \
  groupadd -g 19 cdrom; \
  groupadd -g 20 dialout; \
  groupadd -g 27 video; \
  groupadd -g 78 kvm; \
  groupadd -g 249 input; \
"

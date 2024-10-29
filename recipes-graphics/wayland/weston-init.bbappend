do_install:append () {
if [ -e ${D}${sysconfdir}/profile.d/weston-socket.sh ]; then
   rm -f ${D}${sysconfdir}/profile.d/weston-socket.sh
fi
}

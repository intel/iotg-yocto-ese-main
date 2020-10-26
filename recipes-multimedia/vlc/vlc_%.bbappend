# vlc can use gtk+3
PACKAGECONFIG[notify] = "--enable-notify,--disable-notify, libnotify gdk-pixbuf gtk+3"

#fix live555 search
PACKAGECONFIG[live555] = "--enable-live555 LIVE555_PREFIX=${STAGING_DIR_HOST}${prefix},--disable-live555,live555"


# fix musl build with reallocarray
SRC_URI:append:libc-musl = " file://0001-session.c-musl-lacks-reallocarray-3.patch"

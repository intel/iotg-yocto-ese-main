#! /bin/sh

# pre-init environment
# systemd parallelism makes very early init sequences unreliable
# Runs through any scripts sequentially before starting the real init
if [ -d /etc/preinit-env ]; then
  for i in /etc/preinit-env/*; do
    if [ -x "${i}" ]; then
      "${i}"
    fi
  done
fi

exec /sbin/init "$@"

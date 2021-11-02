FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append:aarch64 = " file://afalg_gcm_sha_hmac.patch \
                           file://0001-return_aad_len_from_afalg.patch \
                         "

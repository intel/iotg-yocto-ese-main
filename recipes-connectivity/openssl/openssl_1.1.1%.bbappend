FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append_aarch64 = " file://afalg_gcm_sha_hmac.patch \
                           file://0001-return_aad_len_from_afalg.patch \
                         "

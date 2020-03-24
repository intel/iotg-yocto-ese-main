FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRCREV_face = "8afa57abc8229d611c4937165d20e2a2d9fc5a12"

SRC_URI_append = " \
                   git://github.com/opencv/opencv_3rdparty.git;branch=contrib_face_alignment_20170818;destsuffix=face;name=face \
                   file://download.patch \
                 "

# OpenCV wants to download more files during configure.  We download these in
# do_fetch and construct a source cache in the format it expects
OPENCV_DLDIR = "${WORKDIR}/downloads"

do_unpack_extra() {
    tar xzf ${WORKDIR}/ipp/ippicv/${IPP_FILENAME} -C ${WORKDIR}

    md5() {
        # Return the MD5 of $1
        echo $(md5sum $1 | cut -d' ' -f1)
    }
    cache() {
        TAG=$1
        shift
        mkdir --parents ${OPENCV_DLDIR}/$TAG
        for F in $*; do
            DEST=${OPENCV_DLDIR}/$TAG/$(md5 $F)-$(basename $F)
            test -e $DEST || ln -s $F $DEST
        done
    }
    cache xfeatures2d/boostdesc ${WORKDIR}/boostdesc/*.i
    cache xfeatures2d/vgg ${WORKDIR}/vgg/*.i
    cache data ${WORKDIR}/face/*.dat
}

EXTRA_OECMAKE_append = " -DOPENCV_GENERATE_PKGCONFIG=ON \
                         -DOPENCV_DOWNLOAD_PATH=${OPENCV_DLDIR} \
                         -DOPENCV_ALLOW_DOWNLOADS=OFF \
                       "

PACKAGECONFIG_append ??= " gapi"

PACKAGECONFIG[gapi] = "-DWITH_ADE=ON -Dade_DIR=${STAGING_LIBDIR},-DWITH_ADE=OFF,ade"

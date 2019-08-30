LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=4f40a941379143186f9602242c3fb729"
SRC_URI = "https://sourceforge.net/projects/gsoap2/files/oldreleases/gsoap_2.8.70.zip;name=gsoap \
           file://0001-Change-config-in-gsoap.patch \
           file://0002-Fix-for-Kclockwork-scan-and-fix-one-possible-crash.patch \
          "
SRC_URI[gsoap.md5sum] = "5704bbfe8ba1c1e8c85130e534ed6368"
SRC_URI[gsoap.sha256sum] = "5b6933394ae1c76faa9a4814c44f74fc8aeef521b57f18d62ae952ecf38d0edd"

S = "${WORKDIR}/gsoap-2.8"

DEPENDS = "json-c util-linux openssl zlib flex bison bison-native"
export PROXY_STRING = "${http_proxy}"

def getproxy(s):
    https_string = "https://"
    http_string = "http://"

    string = "noproxy"

    if s.find(http_string) != -1:
        index = s.find(http_string)
        index += len(http_string)
        string = s[index:]
        string += ":"

    elif s.find(https_string) != -1:
        index = s.find(https_string)
        index += len(https_string)
        string = s[index:]
        string += ":"

    return string

SPLIT_STRING = "${@getproxy("${PROXY_STRING}")}"
do_configure[noexec] = "1"

do_compile() {
    cd ${S}/gsoap/src
    make -f MakefileManual soapcpp2
    cd ../../gsoap/wsdl
    make -f MakefileManual secure
    cd ../../gsoap/bin

    if [ ${SPLIT_STRING} = "noproxy" ]
    then
        ./wsdl2h -o onvif.h -c -s -t ./../typemap.dat -x -n ns -N ns ./www.onvif.org/onvif/ver10/device/wsdl/devicemgmt.wsdl ./www.onvif.org/onvif/ver10/event/wsdl/event.wsdl ./www.onvif.org/onvif/ver10/display.wsdl ./www.onvif.org/onvif/ver10/deviceio.wsdl ./www.onvif.org/onvif/ver20/imaging/wsdl/imaging.wsdl ./www.onvif.org/onvif/ver10/media/wsdl/media.wsdl ./www.onvif.org/onvif/ver20/ptz/wsdl/ptz.wsdl ./www.onvif.org/onvif/ver10/receiver.wsdl ./www.onvif.org/onvif/ver10/recording.wsdl ./www.onvif.org/onvif/ver10/search.wsdl ./www.onvif.org/onvif/ver10/network/wsdl/remotediscovery.wsdl ./www.onvif.org/onvif/ver10/replay.wsdl ./www.onvif.org/onvif/ver20/analytics/wsdl/analytics.wsdl ./www.onvif.org/onvif/ver10/analyticsdevice.wsdl ./www.onvif.org/onvif/ver10/schema/onvif.xsd ./www.onvif.org/onvif/ver10/schema/common.xsd ./www.onvif.org/ver10/actionengine.wsdl
    else
        ./wsdl2h -o onvif.h -r${SPLIT_STRING} -c -s -t ./../typemap.dat -x -n ns -N ns ./www.onvif.org/onvif/ver10/device/wsdl/devicemgmt.wsdl ./www.onvif.org/onvif/ver10/event/wsdl/event.wsdl ./www.onvif.org/onvif/ver10/display.wsdl ./www.onvif.org/onvif/ver10/deviceio.wsdl ./www.onvif.org/onvif/ver20/imaging/wsdl/imaging.wsdl ./www.onvif.org/onvif/ver10/media/wsdl/media.wsdl ./www.onvif.org/onvif/ver20/ptz/wsdl/ptz.wsdl ./www.onvif.org/onvif/ver10/receiver.wsdl ./www.onvif.org/onvif/ver10/recording.wsdl ./www.onvif.org/onvif/ver10/search.wsdl ./www.onvif.org/onvif/ver10/network/wsdl/remotediscovery.wsdl ./www.onvif.org/onvif/ver10/replay.wsdl ./www.onvif.org/onvif/ver20/analytics/wsdl/analytics.wsdl ./www.onvif.org/onvif/ver10/analyticsdevice.wsdl ./www.onvif.org/onvif/ver10/schema/onvif.xsd ./www.onvif.org/onvif/ver10/schema/common.xsd ./www.onvif.org/ver10/actionengine.wsdl   
    fi

    ./soapcpp2 -c -L onvif.h  -x  -I ../../gsoap/import/   -I../../gsoap/
    install -d code_generator/gsoap
    cp ./onvif.h ./code_generator/gsoap
    cp ./soapC.c   ./code_generator/gsoap
    cp ./soapClient.c ./code_generator/gsoap
    cp ./soapServer.c   ./code_generator/gsoap
    cp ./soapH.h ./code_generator/gsoap
    cp ./soapStub.h  ./code_generator/gsoap
    cp ./MediaBinding.nsmap  ./code_generator/gsoap/nsmap.h
    cp ../stdsoap2.h  ./code_generator/gsoap
    cp ../stdsoap2.c  ./code_generator/gsoap
    cp ../custom/duration.c ./code_generator/gsoap
    cd ./code_generator
    patch -p1 < ../../../../0002-Fix-for-Kclockwork-scan-and-fix-one-possible-crash.patch
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gsoap/bin/code_generator/server_onvif ${D}${bindir}
}

INSANE_SKIP_${PN} += "already-stripped"

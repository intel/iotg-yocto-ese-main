DESCRIPTION = "Objective Caml Compiler"
SECTION = "devel"
LICENSE = "LGPL-2.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=ff92c64086fe28f9b64a5e4e3fe24ebb"
SRC_URI = "http://caml.inria.fr/pub/distrib/ocaml-${PV}/ocaml-${PV}.3.tar.gz"
SRC_URI[md5sum] = "ef1a324608c97031cbd92a442d685ab7"

S = "${WORKDIR}/ocaml-${PV}.3"

inherit native staging
DEPENDS = "ocaml-native"
SSTATE_SCAN_FILES = "*.cmt *.cmti *.cmi *.cma *.a *.so \
  extract_crc ocamlmklib ocamlrun ocamloptp ocamllex \
  ocamlc ocamldoc ocamldep ocamlmktop ocamldebug ocamlcp \
  ocamlbuild ocamlbuild.byte ocamlprof ocamlobjinfo ocaml"

do_configure () {
	${S}/configure -prefix ${prefix} -libdir ${libdir} -bindir ${bindir}
}

do_compile () {
	oe_runmake world.opt
}

do_compile:class-native () {
	oe_runmake world
}

do_install () {
	oe_runmake install DESTDIR=${D}
}

BBCLASSEXTEND = "native nativesdk"

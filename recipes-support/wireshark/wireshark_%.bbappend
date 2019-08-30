# ninja fails if cross compiling when build machine and host libdir differs
do_compile_prepend(){
	sed -e 's@ ${libdir} @ @g' -i build.ninja || true
}

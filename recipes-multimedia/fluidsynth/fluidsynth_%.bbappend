
#temporary fix the build error from meta-OE due to the changes on fluidsynth upstream repo.

SRC_URI_remove = "git://github.com/FluidSynth/fluidsynth.git;branch=2.1.x"
SRC_URI_append = " git://github.com/FluidSynth/fluidsynth.git"


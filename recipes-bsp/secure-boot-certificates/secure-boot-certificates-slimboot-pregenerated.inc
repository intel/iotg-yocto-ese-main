python sb_pregen_fixpaths() {
  import os
  path = d.getVar('SBL_KEY_PATH')
  dir = os.path.dirname(path)
  fn = os.path.basename(path)
  d.prependVar('FILESEXTRAPATHS', '%s:' % dir)
  d.setVar('DOWNLOAD_KEY', fn)
}

addhandler sb_pregen_fixpaths
sb_pregen_fixpaths[eventmask] = "bb.event.RecipePreFinalise"

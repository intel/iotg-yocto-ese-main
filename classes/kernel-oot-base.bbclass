# overrides module-base and module class
python() {
    if bb.data.inherits_class('module-base', d):
        pn = d.getVar('PN')
        providers = d.getVar('KERNEL_PROVIDERS') or ''
        for prov in providers.strip().split():
            d.appendVar('BBCLASSEXTEND', ' kernel-oot-module:%s' % prov)
}

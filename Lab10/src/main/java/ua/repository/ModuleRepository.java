package ua.repository;

import ua.model.Module;
import ua.util.Utils;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ModuleRepository extends GenericRepository<Module> {
    private static final Logger LOG = Utils.getLogger();
    public ModuleRepository() {
        super(Module::title);
    }
    public List<Module> sortByTitle() {
        List<Module> list = getAll();
        list.sort(Module.BY_TITLE);
        LOG.info("sortByTitle size=" + list.size());
        return list;
    }
    public List<Module> findByTitlePrefix(String prefix) {
        LOG.info("findByTitlePrefix " + prefix);
        String p = prefix == null ? "" : prefix.toLowerCase();
        return getAll().stream().filter(m -> m.title().toLowerCase().startsWith(p)).collect(Collectors.toList());
    }
    public List<String> mapTitlesCollect() {
        LOG.info("mapTitlesCollect");
        return getAll().stream().map(Module::title).collect(Collectors.toList());
    }
}
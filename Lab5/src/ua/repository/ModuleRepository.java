package ua.repository;

import ua.model.Module;

import java.util.List;

public class ModuleRepository extends GenericRepository<Module> {

    public ModuleRepository() {
        super(Module::title);
    }

    public List<Module> sortByTitle() {
        List<Module> list = getAll();
        list.sort(Module.BY_TITLE);
        return list;
    }
}

package ua.model;

import ua.validation.ModuleValidation;

import java.util.Comparator;

public record Module(String title, String content) implements Comparable<Module> {

    public static final Comparator<Module> BY_TITLE =
            Comparator.comparing(Module::title);

    public Module {
        ModuleValidation.validateModule(title, content);
    }

    @Override
    public int compareTo(Module o) {
        return this.title.compareToIgnoreCase(o.title);
    }

    @Override
    public String toString() {
        return "Module: " + title;
    }
}

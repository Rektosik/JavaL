package ua.model;

import ua.util.Utils;
import ua.util.ValidationHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Module(String title, String content) implements Comparable<Module> {
    public static final Comparator<Module> BY_TITLE = Comparator.comparing(Module::title, String.CASE_INSENSITIVE_ORDER);

    public Module {
        Utils.getLogger().info("Attempting to create Module: " + title);
        List<String> errors = new ArrayList<>();

        ValidationHelper.checkNonEmpty(title, "title", errors);
        ValidationHelper.checkNonEmpty(content, "content", errors);

        ValidationHelper.validateAndThrow(errors, "Module");
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
package ua.validation;

import ua.util.Utils;

public final class ModuleValidation {
    private ModuleValidation() {}

    public static void validateModule(String title, String content) {
        Utils.requireNonEmpty(title, "Module title");
        Utils.requireNonEmpty(content, "Module content");
    }

    public static void validateTitle(String title) {
        Utils.requireNonEmpty(title, "Module title");
    }

    public static void validateContent(String content) {
        Utils.requireNonEmpty(content, "Module content");
    }
}

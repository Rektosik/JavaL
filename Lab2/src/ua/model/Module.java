package ua.model;

import ua.util.Utils;

public record Module(String title, String content) {
    public Module {
        Utils.requireNonEmpty(title, "Module title");
        Utils.requireNonEmpty(content, "Module content");
    }

    @Override
    public String toString() {
        return "Module: " + title;
    }
}


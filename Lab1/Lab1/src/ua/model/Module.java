package ua.model;

import ua.util.Utils;

public class Module {
    private String title;
    private String content;

    public Module(String title, String content) {
        Utils.requireNonEmpty(title, "Module title");
        Utils.requireNonEmpty(content, "Module content");
        this.title = title;
        this.content = content;
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }

    @Override
    public String toString() {
        return "Module: " + title;
    }
}


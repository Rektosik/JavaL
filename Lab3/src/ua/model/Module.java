package ua.model;

import ua.validation.ModuleValidation;

public record Module(String title, String content) {
    public Module {
        ModuleValidation.validateModule(title, content);
    }

    @Override
    public String toString() {
        return "Module: " + title;
    }
}

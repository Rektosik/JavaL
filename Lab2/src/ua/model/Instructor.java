package ua.model;

import ua.util.Utils;

public record Instructor(String firstName, String lastName, String expertise) {
    public Instructor {
        Utils.requireNonEmpty(firstName, "First name");
        Utils.requireNonEmpty(lastName, "Last name");
        Utils.requireNonEmpty(expertise, "Expertise");
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + expertise + ")";
    }
}


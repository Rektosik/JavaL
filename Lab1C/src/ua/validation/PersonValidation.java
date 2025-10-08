package ua.validation;

import ua.util.Utils;

public final class PersonValidation {
    private PersonValidation() {}

    public static void requireNonEmptyName(String firstName, String lastName) {
        Utils.requireNonEmpty(firstName, "First name");
        Utils.requireNonEmpty(lastName, "Last name");
    }

    public static void requireNonEmptyFirstName(String firstName) {
        Utils.requireNonEmpty(firstName, "First name");
    }

    public static void requireNonEmptyLastName(String lastName) {
        Utils.requireNonEmpty(lastName, "Last name");
    }
}


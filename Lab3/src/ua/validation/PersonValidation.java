package ua.validation;

import ua.util.Utils;

public final class PersonValidation {
    private PersonValidation() {}

    public static void requireNonEmptyName(String firstName, String lastName) {
        Utils.requireNonEmpty(firstName, "First name");
        Utils.requireNonEmpty(lastName, "Last name");
    }
}

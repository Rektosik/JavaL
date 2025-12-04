package ua.model;

import ua.util.ValidationHelper;

public record Person(String firstName, String lastName) {
    public Person {
        ValidationHelper.requireNonEmpty(firstName, "firstName");
        ValidationHelper.requireNonEmpty(lastName, "lastName");
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
package ua.model;

import ua.validation.PersonValidation;

public record Person(String firstName, String lastName) {
    public Person {
        PersonValidation.requireNonEmptyName(firstName, lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}


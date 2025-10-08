package ua.model;

import ua.validation.PersonValidation;

public abstract class Person {
    protected String firstName;
    protected String lastName;

    public Person(String firstName, String lastName) {
        PersonValidation.requireNonEmptyName(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setFirstName(String firstName) {
        PersonValidation.requireNonEmptyFirstName(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        PersonValidation.requireNonEmptyLastName(lastName);
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}


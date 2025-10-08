package ua.model;

import ua.util.Utils;

public class Instructor extends Person {
    private String expertise;

    public Instructor(String firstName, String lastName, String expertise) {
        super(firstName, lastName);
        Utils.requireNonEmpty(expertise, "Expertise");
        this.expertise = expertise;
    }

    public String getExpertise() { return expertise; }

    @Override
    public String toString() {
        return super.toString() + " (" + expertise + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instructor)) return false;
        Instructor other = (Instructor) o;
        return firstName.equals(other.firstName) && lastName.equals(other.lastName);
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode();
    }
}

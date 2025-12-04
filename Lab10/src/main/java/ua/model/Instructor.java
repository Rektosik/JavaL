package ua.model;

import ua.util.Utils;
import ua.util.ValidationHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Instructor(String firstName, String lastName, String expertise) implements Comparable<Instructor> {
    public static final Comparator<Instructor> BY_LASTNAME = Comparator.comparing(Instructor::lastName, String.CASE_INSENSITIVE_ORDER);

    public Instructor {
        List<String> errors = new ArrayList<>();

        ValidationHelper.checkNonEmpty(firstName, "firstName", errors);
        ValidationHelper.checkNonEmpty(lastName, "lastName", errors);
        ValidationHelper.checkNonEmpty(expertise, "expertise", errors);

        ValidationHelper.validateAndThrow(errors, "Instructor");
    }

    @Override
    public int compareTo(Instructor o) {
        return this.lastName.compareToIgnoreCase(o.lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + expertise + ")";
    }
}
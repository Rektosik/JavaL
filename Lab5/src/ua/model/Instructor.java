package ua.model;

import ua.util.Utils;
import java.util.Comparator;

public record Instructor(String firstName, String lastName, String expertise)
        implements Comparable<Instructor> {

    public static final Comparator<Instructor> BY_LASTNAME =
            Comparator.comparing(Instructor::lastName);

    public Instructor {
        Utils.requireNonEmpty(firstName, "First name");
        Utils.requireNonEmpty(lastName, "Last name");
        Utils.requireNonEmpty(expertise, "Expertise");
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

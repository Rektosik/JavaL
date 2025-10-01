package ua.model;

import ua.util.Utils;
import java.time.LocalDate;

public record Student(String firstName, String lastName, String email, LocalDate enrollmentDate) {
    public Student {
        Utils.requireNonEmpty(firstName, "First name");
        Utils.requireNonEmpty(lastName, "Last name");
        Utils.requireEmail(email);
        Utils.requireDateNotPast(enrollmentDate, "Enrollment date");
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + email + ", enrolled " +
                Utils.formatDate(enrollmentDate) + ")";
    }
}


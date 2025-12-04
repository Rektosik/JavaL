package ua.model;

import ua.util.Utils;
import ua.util.ValidationHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Student(String firstName, String lastName, String email, String enrollmentDate) implements Comparable<Student> {

    public static final Comparator<Student> BY_LASTNAME = Comparator.comparing(Student::lastName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Student> BY_DATE = Comparator.comparing(Student::enrollmentDate);

    public Student {
        List<String> errors = new ArrayList<>();

        ValidationHelper.checkNonEmpty(firstName, "firstName", errors);
        ValidationHelper.checkNonEmpty(lastName, "lastName", errors);
        ValidationHelper.checkEmail(email, errors);
        ValidationHelper.checkNonEmpty(enrollmentDate, "enrollmentDate", errors);

        ValidationHelper.validateAndThrow(errors, "Student");
    }

    @Override
    public int compareTo(Student o) {
        return this.email.compareToIgnoreCase(o.email);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + email + ", enrolled " + enrollmentDate + ")";
    }
}
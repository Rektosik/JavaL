package ua.model;

import ua.util.Utils;
import ua.validation.StudentValidation;

import java.util.Comparator;

public record Student(String firstName, String lastName, String email, String enrollmentDate) implements Comparable<Student> {

    public static final Comparator<Student> BY_LASTNAME = Comparator.comparing(Student::lastName, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Student> BY_DATE = Comparator.comparing(Student::enrollmentDate);

    public Student {
        Utils.requireNonEmpty(firstName, "First name");
        Utils.requireNonEmpty(lastName, "Last name");
        StudentValidation.requireValidEmail(email);

        Utils.requireNonEmpty(enrollmentDate, "Enrollment date");
    }

    @Override
    public int compareTo(Student o) {
        return this.email.compareToIgnoreCase(o.email);
    }

    @Override
    public String toString() {
        // 4. Прибрали Utils.formatDate(...), бо це вже і так рядок
        return firstName + " " + lastName + " (" + email + ", enrolled " + enrollmentDate + ")";
    }
}
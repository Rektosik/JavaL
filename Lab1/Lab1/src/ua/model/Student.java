package ua.model;

import ua.util.Utils;
import java.time.LocalDate;

public class Student extends Person {
    private String email;
    private LocalDate enrollmentDate;

    public Student(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        super(firstName, lastName);
        Utils.requireEmail(email);
        Utils.requireDateNotPast(enrollmentDate, "Enrollment date");
        this.email = email;
        this.enrollmentDate = enrollmentDate;
    }

    public static Student of(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        return new Student(firstName, lastName, email, enrollmentDate);
    }

    public String getEmail() { return email; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }

    @Override
    public String toString() {
        return super.toString() + " (" + email + ", enrolled " + Utils.formatDate(enrollmentDate) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student other = (Student) o;
        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}

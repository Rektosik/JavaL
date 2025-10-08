package ua.model;

import ua.util.Utils;
import ua.validation.StudentValidation;
import java.time.LocalDate;

public class Student extends Person {
    private String email;
    private LocalDate enrollmentDate;

    public Student(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        super(firstName, lastName);
        setEmail(email);
        setEnrollmentDate(enrollmentDate);
    }

    public static Student of(String firstName, String lastName, String email, LocalDate enrollmentDate) {
        return new Student(firstName, lastName, email, enrollmentDate);
    }

    public String getEmail() { return email; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }

    public void setEmail(String email) {
        StudentValidation.requireValidEmail(email);
        this.email = email;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        StudentValidation.requireDateNotPast(enrollmentDate, "Enrollment date");
        this.enrollmentDate = enrollmentDate;
    }

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

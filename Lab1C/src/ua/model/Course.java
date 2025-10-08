package ua.model;

import ua.util.Utils;
import ua.validation.CourseValidation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private String title;
    private String description;
    private int credits;
    private LocalDate startDate;
    protected Instructor instructor;
    private List<Student> students = new ArrayList<>();

    public Course(String title, String description, int credits, LocalDate startDate, Instructor instructor) {
        CourseValidation.requireValidCourseData(title, description, credits, startDate, instructor);
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.startDate = startDate;
        this.instructor = instructor;
    }

    public static Course of(String title, String description, int credits, LocalDate startDate, Instructor instructor) {
        return new Course(title, description, credits, startDate, instructor);
    }

    public String getTitle() { return title; }
    public Instructor getInstructor() { return instructor; }
    public String getDescription() { return description; }
    public int getCredits() { return credits; }
    public LocalDate getStartDate() { return startDate; }
    public List<Student> getStudents() { return new ArrayList<>(students); }

    public void setTitle(String title) {
        CourseValidation.requireNonEmptyTitle(title);
        this.title = title;
    }

    public void setDescription(String description) {
        CourseValidation.requireNonEmptyDescription(description);
        this.description = description;
    }

    public void setCredits(int credits) {
        CourseValidation.requirePositiveCredits(credits);
        this.credits = credits;
    }

    public void setStartDate(LocalDate startDate) {
        CourseValidation.requireDateNotPast(startDate, "Start date");
        this.startDate = startDate;
    }

    public void setInstructor(Instructor instructor) {
        CourseValidation.requireInstructorNotNull(instructor);
        this.instructor = instructor;
    }

    // робота зі студентами
    public void addStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Student cannot be null");
        students.add(student);
    }

    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    @Override
    public String toString() {
        return "Course: " + title + " (" + credits + " credits, starts " + Utils.formatDate(startDate) + ")\n"
                + "Instructor: " + instructor + "\n"
                + "Students enrolled: " + students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course other = (Course) o;
        return Objects.equals(title, other.title) && Objects.equals(startDate, other.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDate);
    }
}

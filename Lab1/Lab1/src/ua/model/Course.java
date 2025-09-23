package ua.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private String title;
    private String description;
    private int credits;
    private LocalDate startDate;
    protected Instructor instructor; // protected — доступний у підкласах
    private List<Student> students = new ArrayList<>();

    public Course(String title, String description, int credits, LocalDate startDate, Instructor instructor) {
        Utils.requireNonEmpty(title, "Course title");
        Utils.requireNonEmpty(description, "Course description");
        Utils.requirePositive(credits, "Credits");
        Utils.requireDateNotPast(startDate, "Start date");
        if (instructor == null) throw new IllegalArgumentException("Instructor cannot be null");
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.startDate = startDate;
        this.instructor = instructor;
    }

    public static Course of(String title, String description, int credits, LocalDate startDate, Instructor instructor) {
        return new Course(title, description, credits, startDate, instructor);
    }

    public void addStudent(Student student) { students.add(student); }

    public String getTitle() { return title; }
    public Instructor getInstructor() { return instructor; }

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


package ua.model;

import ua.util.Utils;
import java.time.LocalDate;
import java.util.List;

public record Course(String title, String description, int credits,
                     LocalDate startDate, Instructor instructor,
                     List<Student> students, CourseLevel level) {

    public Course {
        Utils.requireNonEmpty(title, "Course title");
        Utils.requireNonEmpty(description, "Course description");
        Utils.requirePositive(credits, "Credits");
        Utils.requireDateNotPast(startDate, "Start date");
        if (instructor == null) throw new IllegalArgumentException("Instructor cannot be null");
        if (students == null) students = List.of();
    }

    public static Course of(String title, String description, int credits,
                            LocalDate startDate, Instructor instructor, CourseLevel level) {
        return new Course(title, description, credits, startDate, instructor, List.of(), level);
    }

    @Override
    public String toString() {
        return "Course: " + title + " (" + level + ", " + credits + " credits, starts " +
                Utils.formatDate(startDate) + ")\nInstructor: " + instructor +
                "\nStudents enrolled: " + students.size();
    }
}


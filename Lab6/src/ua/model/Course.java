package ua.model;

import ua.util.Utils;
import ua.validation.CourseValidation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Course(String title, String description, int credits, LocalDate startDate, Instructor instructor, CourseLevel level, List<Student> students) implements Comparable<Course> {
    public static final Comparator<Course> BY_CREDITS = Comparator.comparingInt(Course::credits);
    public static final Comparator<Course> BY_START_DATE = Comparator.comparing(Course::startDate);
    public Course {
        CourseValidation.requireValidCourseData(title, description, credits, startDate, instructor);
        if (level == null) throw new IllegalArgumentException("Course level cannot be null");
    }
    @Override
    public int compareTo(Course o) {
        return this.title.compareToIgnoreCase(o.title);
    }
    @Override
    public String toString() {
        return "Course: " + title + " (" + credits + " credits, " + level + ") Instructor: " + instructor;
    }
}

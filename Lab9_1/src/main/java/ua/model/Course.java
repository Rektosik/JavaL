package ua.model;

import ua.util.Utils;
import ua.util.ValidationHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Course(String title, String description, int credits, LocalDate startDate, Instructor instructor, CourseLevel level, List<Student> students) implements Comparable<Course> {
    public static final Comparator<Course> BY_CREDITS = Comparator.comparingInt(Course::credits);
    public static final Comparator<Course> BY_START_DATE = Comparator.comparing(Course::startDate);

    public Course {
        List<String> errors = new ArrayList<>();

        ValidationHelper.checkNonEmpty(title, "title", errors);
        ValidationHelper.checkNonEmpty(description, "description", errors);
        ValidationHelper.checkPositive(credits, "credits", errors);
        ValidationHelper.checkDateNotPast(startDate, "startDate", errors);
        ValidationHelper.checkNotNull(instructor, "instructor", errors);
        ValidationHelper.checkNotNull(level, "level", errors);

        ValidationHelper.validateAndThrow(errors, "Course");
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
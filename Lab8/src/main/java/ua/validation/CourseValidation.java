package ua.validation;

import ua.model.Instructor;
import ua.util.Utils;

import java.time.LocalDate;

public final class CourseValidation {
    private CourseValidation() {}

    public static void requireValidCourseData(String title, String description, int credits, LocalDate startDate, Instructor instructor) {
        Utils.requireNonEmpty(title, "Course title");
        Utils.requireNonEmpty(description, "Course description");
        Utils.requirePositive(credits, "Credits");
        Utils.requireDateNotPast(startDate, "Start date");
        if (instructor == null) throw new IllegalArgumentException("Instructor cannot be null");
    }
}

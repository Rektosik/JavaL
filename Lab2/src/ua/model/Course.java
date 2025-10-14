package ua.model;

import ua.validation.CourseValidation;
import ua.util.Utils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record Course(
        String title,
        String description,
        int credits,
        LocalDate startDate,
        Instructor instructor,
        CourseLevel level,
        List<Student> students
) {
    public Course {
        CourseValidation.requireValidCourseData(title, description, credits, startDate, instructor);
        if (level == null) throw new IllegalArgumentException("Course level cannot be null");
        students = students == null ? new ArrayList<>() : new ArrayList<>(students);
    }

    public Course addStudent(Student s) {
        List<Student> copy = new ArrayList<>(students);
        copy.add(s);
        return new Course(title, description, credits, startDate, instructor, level, copy);
    }

    @Override
    public String toString() {
        return "Course: " + title + " (" + credits + " credits, " + level + ")\n"
                + "Instructor: " + instructor + "\n"
                + "Starts: " + Utils.formatDate(startDate) + "\n"
                + "Students: " + students.size();
    }
}

package ua;

import org.junit.jupiter.api    .Test;
import ua.model.Course;
import ua.model.CourseLevel;
import ua.model.Instructor;
import ua.model.Student;
import ua.util.InvalidDataException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTests {

    @Test
    void testValidStudentCreation() {
        assertDoesNotThrow(() -> {
            new Student("Alice", "Smith", "alice@test.com", "2025-01-01");
        });
    }

    @Test
    void testInvalidStudentCreation_AllErrors() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            new Student("", "", "bad-email", "");
        });

        String msg = exception.getMessage();
        assertTrue(msg.contains("firstName: cannot be empty"));
        assertTrue(msg.contains("lastName: cannot be empty"));
        assertTrue(msg.contains("email: invalid format"));
        assertTrue(msg.contains("enrollmentDate: cannot be empty"));
    }

    @Test
    void testCourseValidation() {
        Instructor instructor = new Instructor("John", "Doe", "IT");

        assertDoesNotThrow(() -> new Course("Java", "Basic", 5, LocalDate.now().plusDays(1), instructor, CourseLevel.BEGINNER, null));

        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> {
            new Course("", "Desc", -5, LocalDate.now().minusDays(1), instructor, CourseLevel.BEGINNER, null);
        });

        assertTrue(ex.getMessage().contains("title: cannot be empty"));
        assertTrue(ex.getMessage().contains("credits: must be > 0"));
        assertTrue(ex.getMessage().contains("startDate: cannot be in the past"));
    }

    @Test
    void testInstructorValidation() {
        InvalidDataException ex = assertThrows(InvalidDataException.class, () -> {
            new Instructor(null, "   ", null);
        });

        assertTrue(ex.getMessage().contains("firstName: cannot be empty"));
        assertTrue(ex.getMessage().contains("lastName: cannot be empty"));
        assertTrue(ex.getMessage().contains("expertise: cannot be null") || ex.getMessage().contains("expertise: cannot be empty"));
    }
}
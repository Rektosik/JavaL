package ua.test;

import ua.model.*;
import ua.util.InvalidDataException;
import ua.util.Utils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BasicTests {

    public static void main(String[] args) {

        System.out.println("======= TESTING START =======");

        testStudentCreation();
        testInvalidData();
        testCourseLogic();

        System.out.println("======= TESTING FINISHED =======");
    }

    private static void testStudentCreation() {
        System.out.println("\n--- Test: Correct student creation ---");

        try {
            Student s = new Student(
                    "Ivan",
                    "Kovalenko",
                    "ivan@mail.com",
                    LocalDate.now().plusDays(5)
            );
            System.out.println("[OK] Student created: " + s);
        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected exception: " + e.getMessage());
        }
    }

    private static void testInvalidData() {
        System.out.println("\n--- Test: Invalid data handling ---");

        try {
            Utils.readDataFromFile("file_that_does_not_exist.txt");
            System.out.println("[FAIL] Expected InvalidDataException");
        } catch (InvalidDataException e) {
            System.out.println("[OK] InvalidDataException caught: " + e.getMessage());
        }

        try {
            Student wrong = new Student(
                    "Ivan",
                    "",                     // некоректні дані
                    "invalid_email",        // некоректний email
                    LocalDate.now().minusDays(1)
            );
            System.out.println("[FAIL] Constructor should throw exception");
        } catch (Exception e) {
            System.out.println("[OK] Exception caught: " + e.getMessage());
        }
    }

    private static void testCourseLogic() {
        System.out.println("\n--- Test: Course logic methods ---");

        try {
            Instructor instructor = new Instructor("John", "Doe", "OOP");
            ua.model.Module module = new ua.model.Module("Java Basics", "Classes, inheritance");

            List<Student> studentList = new ArrayList<>();
            studentList.add(new Student("Mark", "Brown", "mark@mail.com", LocalDate.now().plusDays(10)));

            Course course = new Course(
                    "Programming",
                    "Intro to Java",
                    5,
                    LocalDate.now().plusDays(3),
                    instructor,
                    CourseLevel.BEGINNER,
                    studentList
            );

            course = course.addStudent(new Student("Alice", "Smith", "alice@mail.com", LocalDate.now().plusDays(2)));

            if (course.students().size() == 2)
                System.out.println("[OK] addStudent() works correctly");
            else
                System.out.println("[FAIL] addStudent() result incorrect");

            System.out.println("[INFO] toString() output:\n" + course);

        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected error: " + e.getMessage());
        }
    }
}

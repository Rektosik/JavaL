package ua;

import ua.model.*;
import ua.util.Utils;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Utils.printSeparator();

        Instructor instructor = new Instructor("John", "Doe", "Computer Science");
        Student s1 = new Student("Alice", "Smith", "alice@mail.com", LocalDate.now());
        Student s2 = new Student("Bob", "Brown", "bob@mail.com", LocalDate.now().plusDays(1));

        ua.model.Module module = new ua.model.Module("OOP", "Classes, inheritance, polymorphism");
        Assignment assignment = new Assignment(module, LocalDate.now().plusDays(10), 100);

        Course course = new Course(
                "Java Basics",
                "Introduction to Java",
                5,
                LocalDate.now().plusDays(2),
                instructor,
                CourseLevel.BEGINNER,
                List.of(s1, s2)
        );

        System.out.println(instructor);
        System.out.println(s1);
        System.out.println(course);
        System.out.println(module);
        System.out.println(assignment);

        Utils.printSeparator();

        CourseLevel level = CourseLevel.ADVANCED;
        String message = switch (level) {
            case BEGINNER -> "Perfect for new learners";
            case INTERMEDIATE -> "Good for those with some experience";
            case ADVANCED -> "Challenging and in-depth content";
        };
        System.out.println("Course level description: " + level.getDescription());
        System.out.println("Switch message: " + message);

        Utils.printSeparator();
    }
}


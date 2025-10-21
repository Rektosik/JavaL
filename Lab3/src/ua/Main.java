package ua;

import ua.model.*;
import ua.util.Utils;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Utils.printSeparator();

        Instructor inst = new Instructor("John", "Doe", "Computer Science");
        Student s1 = new Student("Alice", "Smith", "alice@mail.com", LocalDate.now());
        Student s2 = new Student("Bob", "Brown", "bob@mail.com", LocalDate.now().plusDays(1));

        Course course = new Course(
                "Java Basics",
                "Intro to Java",
                5,
                LocalDate.now().plusDays(2),
                inst,
                List.of(s1, s2),
                CourseLevel.BEGINNER
        );

        ua.model.Module m1 = new ua.model.Module("OOP", "Classes, inheritance, polymorphism");
        Assignment a1 = new Assignment(m1, LocalDate.now().plusDays(10), 100);

        System.out.println(inst);
        System.out.println(s1);
        System.out.println(course);
        System.out.println(m1);
        System.out.println(a1);

        Utils.printSeparator();
        
        printCourseAdvice(course.level());
    }

    static void printCourseAdvice(CourseLevel level) {
        String advice = switch (level) {
            case BEGINNER -> "Start with the basics!";
            case INTERMEDIATE -> "You already know the basics, now dive deeper!";
            case ADVANCED -> "Time to tackle complex projects!";
        };
        System.out.println("Advice for this course: " + advice);
    }
}

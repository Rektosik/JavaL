package ua;

import ua.model.Instructor;
import ua.model.Student;
import ua.model.Course;
import ua.model.Module;
import ua.model.Assignment;
import ua.util.Utils;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Utils.printSeparator();

        Instructor inst = new Instructor("John", "Doe", "Computer Science");
        Student s1 = new Student("Alice", "Smith", "alice@mail.com", LocalDate.now());
        Student s2 = Student.of("Bob", "Brown", "bob@mail.com", LocalDate.now().plusDays(1));

        Course course = Course.of("Java Basics", "Intro to Java", 5, LocalDate.now().plusDays(2), inst);
        course.addStudent(s1);
        course.addStudent(s2);
        
        Module m1 = new Module("OOP", "Classes, inheritance, polymorphism");
        Assignment a1 = new Assignment(m1, LocalDate.now().plusDays(10), 100);

        System.out.println(inst);
        System.out.println(s1);
        System.out.println(course);
        System.out.println(m1);
        System.out.println(a1);

        Utils.printSeparator();

        try {
            Student bad = new Student("", "Ivanov", "bad-email", LocalDate.now().minusDays(5));
        } catch (Exception e) {
            System.out.println("Validation error (expected): " + e.getMessage());
        }

        Utils.printSeparator();
    }
}

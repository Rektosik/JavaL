package ua;

import ua.model.*;
import ua.util.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        Logger log = Utils.getLogger();

        Utils.printSeparator();

        String path = "students.txt";
        Utils.createExampleFile(path);

        try {
            // ✅ зчитуємо через Parser (Utils.readDataFromFile)
            List<String[]> studentData = Utils.readDataFromFile(path);

            List<Student> students = new ArrayList<>();

            System.out.println(">>> Parsed students:");
            for (String[] s : studentData) {
                try {
                    Student student = new Student(
                            s[0].trim(),
                            s[1].trim(),
                            s[2].trim(),
                            LocalDate.parse(s[3].trim())
                    );

                    students.add(student);
                    System.out.println(" + " + student);

                } catch (Exception e) {
                    System.out.println(" - Invalid student skipped: " + Arrays.toString(s));
                }
            }

            Instructor instructor = new Instructor("John", "Doe", "Computer Science");

            ua.model.Module module = new ua.model.Module(
                    "OOP",
                    "Classes, inheritance, polymorphism"
            );

            Assignment assignment = new Assignment(
                    module,
                    LocalDate.now().plusDays(10),
                    100
            );

            Course course = new Course(
                    "Java Basics",
                    "Introduction to Java",
                    5,
                    LocalDate.now().plusDays(2),
                    instructor,
                    CourseLevel.BEGINNER,
                    students
            );

            System.out.println("\n>>> COURSE CREATED <<<");
            System.out.println(course);

        } catch (InvalidDataException e) {
            log.warning("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            log.severe("Unexpected error: " + e.getMessage());
        }
    }
}


package ua;

import ua.model.*;
import ua.util.*;
import ua.repository.GenericRepository;
import ua.repository.IdentityExtractor;
import ua.test.RepositoryTests;

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
            List<String[]> studentData = Utils.readDataFromFile(path);
            List<Student> students = new ArrayList<>();

            for (String[] s : studentData) {
                try {
                    Student st = new Student(
                            s[0].trim(),
                            s[1].trim(),
                            s[2].trim(),
                            LocalDate.parse(s[3].trim())
                    );
                    students.add(st);
                    log.info("Created: " + st);
                } catch (Exception e) {
                    log.warning("Invalid student: " + Arrays.toString(s) + " → " + e.getMessage());
                }
            }

            Instructor instructor = new Instructor("John", "Doe", "Computer Science");
            ua.model.Module module = new ua.model.Module("OOP", "Classes, inheritance, polymorphism");
            Assignment assignment = new Assignment(module, LocalDate.now().plusDays(10), 100);

            Course course = new Course(
                    "Java Basics",
                    "Introduction to Java",
                    5,
                    LocalDate.now().plusDays(2),
                    instructor,
                    CourseLevel.BEGINNER,
                    students
            );

            System.out.println(course);
            log.info("Course created successfully.");

        } catch (InvalidDataException e) {
            log.warning("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            log.severe("Unexpected error: " + e.getMessage());
        } finally {
            log.info("=== Program finished (initial) ===");
            Utils.printSeparator();
        }

        System.out.println("\n--- Repository demonstration ---");
        try {
            IdentityExtractor<Student> emailExtractor = Student::email;
            GenericRepository<Student> repo = new GenericRepository<>(emailExtractor);

            try {
                List<String[]> fileData = Utils.readDataFromFile(path);
                for (String[] row : fileData) {
                    try {
                        Student s = new Student(row[0].trim(), row[1].trim(), row[2].trim(), LocalDate.parse(row[3].trim()));
                        try {
                            repo.add(s);
                        } catch (IllegalArgumentException ex) {
                            log.warning("Repo add skipped: " + ex.getMessage());
                        }
                    } catch (Exception ex) {
                        log.warning("Cannot build student from row: " + Arrays.toString(row) + " -> " + ex.getMessage());
                    }
                }
            } catch (InvalidDataException ex) {
                log.warning("Repository demo: cannot read file: " + ex.getMessage());
            }

            System.out.println("Repo size after demo load: " + repo.size());
            repo.getAll().forEach(st -> System.out.println(" - " + st));

        } catch (Exception e) {
            log.severe("Repository demo failed: " + e.getMessage());
        }

        RepositoryTests.runAll();

        log.info("=== Program finished (all) ===");
    }
}


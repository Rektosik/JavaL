package ua;

import ua.model.Student;
import ua.repository.StudentRepository;
import ua.util.InvalidDataException;
import ua.util.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger log = Utils.getLogger();
        Utils.printSeparator();

        String path = "students.txt";
        Utils.createExampleFile(path);

        List<String[]> raw;
        try {
            raw = Utils.readDataFromFile(path);
        } catch (InvalidDataException e) {
            log.severe("Cannot read students file: " + e.getMessage());
            return;
        }

        System.out.println("\n--- RAW LINES (from file) ---");
        raw.forEach(arr -> System.out.println(String.join(" | ", arr)));

        List<Student> parsed = new ArrayList<>();
        for (String[] r : raw) {
            try {
                Student s = new Student(r[0].trim(), r[1].trim(), r[2].trim(), LocalDate.parse(r[3].trim()));
                parsed.add(s);
            } catch (Exception ex) {
                log.warning("Skipping invalid row: " + String.join(",", r) + " -> " + ex.getMessage());
            }
        }

        System.out.println("\n--- Parsed students (valid) ---");
        parsed.forEach(s -> System.out.println(s));

        StudentRepository repo = new StudentRepository();

        System.out.println("\n--- Adding parsed students to repository ---");
        for (Student s : parsed) {
            try {
                repo.add(s);
                log.info("Added to repo: " + s.email());
            } catch (Exception ex) {
                log.warning("Failed to add to repo: " + s + " -> " + ex.getMessage());
            }
        }

        System.out.println("\n--- Repository contents (initial insertion order) ---");
        repo.getAll().forEach(System.out::println);

        System.out.println("\n--- Adding NEW students programmatically ---");
        Student new1 = new Student("Ivan", "Koval", "ivan.koval@mail.com", LocalDate.now().plusDays(5));
        Student new2 = new Student("Oksana", "Melnyk", "oksana.m@mail.com", LocalDate.now().plusDays(2));
        try { repo.add(new1); log.info("Added new: " + new1.email()); } catch (Exception ex) { log.warning(ex.getMessage()); }
        try { repo.add(new2); log.info("Added new: " + new2.email()); } catch (Exception ex) { log.warning(ex.getMessage()); }

        System.out.println("\n--- Repository contents (after adding new students) ---");
        repo.getAll().forEach(System.out::println);

        System.out.println("\n--- Sort by identity (email) ASC ---");
        repo.sortByIdentity("ASC");
        repo.getAll().forEach(System.out::println);

        System.out.println("\n--- Sort by identity (email) DESC ---");
        repo.sortByIdentity("DESC");
        repo.getAll().forEach(System.out::println);

        System.out.println("\n--- Sorted by last name (using comparator) ---");
        repo.sortByLastName().forEach(System.out::println);

        System.out.println("\n--- Sorted by enrollment date (using comparator) ---");
        repo.sortByEnrollmentDate().forEach(System.out::println);

        Utils.printSeparator();
        log.info("Demo finished.");
    }
}


package ua;

import ua.model.Student;
import ua.repository.StudentRepository;
import ua.repository.CourseRepository;
import ua.repository.ModuleRepository;
import ua.repository.InstructorRepository;
import ua.util.InvalidDataException;
import ua.util.Utils;
import ua.model.Course;
import ua.model.Module;
import ua.model.Instructor;
import ua.model.CourseLevel;

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

        System.out.println("\n=== ADDITIONAL REPOSITORIES DEMO ===");

        CourseRepository courseRepo = new CourseRepository();
        ModuleRepository moduleRepo = new ModuleRepository();
        InstructorRepository instructorRepo = new InstructorRepository();

        Instructor i1 = new Instructor("John","Doe","Databases");
        Instructor i2 = new Instructor("Ann","Smith","Security");
        instructorRepo.add(i1);
        instructorRepo.add(i2);

        Course c1 = new Course("Java Basics","Intro to Java",5, LocalDate.now().plusDays(3), i1, CourseLevel.BEGINNER, new ArrayList<>());
        Course c2 = new Course("Databases","SQL and NoSQL",3, LocalDate.now().plusDays(10), i2, CourseLevel.INTERMEDIATE, new ArrayList<>());
        courseRepo.add(c1);
        courseRepo.add(c2);

        Module m1 = new Module("OOP","Classes and objects");
        Module m2 = new Module("SQL","Queries and joins");
        moduleRepo.add(m1);
        moduleRepo.add(m2);

        System.out.println("\n--- CourseRepository: findByTitle 'Java Basics' ---");
        courseRepo.findByTitle("Java Basics").forEach(System.out::println);

        System.out.println("\n--- CourseRepository: findByCreditsRange 2..5 ---");
        courseRepo.findByCreditsRange(2,5).forEach(System.out::println);

        System.out.println("\n--- ModuleRepository: findByTitlePrefix 'S' ---");
        moduleRepo.findByTitlePrefix("S").forEach(System.out::println);

        System.out.println("\n--- InstructorRepository: findByExpertise 'Sec' ---");
        instructorRepo.findByExpertise("Sec").forEach(System.out::println);

        System.out.println("\n--- Collect example: courseRepo.collectStudentEmailsFromCourses() ---");
        System.out.println(courseRepo.collectStudentEmailsFromCourses());

        System.out.println("\n--- Map example: moduleRepo.mapTitlesCollect() ---");
        moduleRepo.mapTitlesCollect().forEach(System.out::println);

        System.out.println("\n--- Reduce example: student emails reduce ---");
        repo.emailsReduce().ifPresent(System.out::println);

        System.out.println("\n--- FlatMap example: collect all student emails from courses (flatMap) ---");
        System.out.println(courseRepo.collectStudentEmailsFromCourses());

        System.out.println("\n--- stream vs parallelStream performance (count by last name) ---");
        String sampleLast = repo.getAll().isEmpty() ? "Smith" : repo.getAll().get(0).lastName();
        long t0 = System.nanoTime();
        long seq = repo.countByLastNameSequential(sampleLast);
        long t1 = System.nanoTime();
        long par = repo.countByLastNameParallel(sampleLast);
        long t2 = System.nanoTime();
        System.out.printf("seq=%d time=%dms, par=%d time=%dms%n", seq, (t1-t0)/1_000_000, par, (t2-t1)/1_000_000);

        Utils.printSeparator();
        log.info("Extended demo finished.");
    }
}

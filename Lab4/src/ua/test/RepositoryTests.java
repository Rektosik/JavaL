package ua.test;

import ua.model.Student;
import ua.repository.GenericRepository;
import ua.repository.IdentityExtractor;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import ua.util.Utils;

public class RepositoryTests {
    private static final Logger log = Utils.getLogger();

    public static void runAll() {
        System.out.println("\n=== Running RepositoryTests ===");
        testStudentRepository();
        System.out.println("=== RepositoryTests finished ===\n");
    }

    private static void testStudentRepository() {
        try {
            IdentityExtractor<Student> emailExtractor = Student::email; // uses record accessor
            GenericRepository<Student> repo = new GenericRepository<>(emailExtractor);

            Student s1 = new Student("Anna", "One", "anna@mail.com", LocalDate.now().plusDays(5));
            Student s2 = new Student("Ben", "Two", "ben@mail.com", LocalDate.now().plusDays(6));
            Student s3 = new Student("Carl", "Three", "carl@mail.com", LocalDate.now().plusDays(7));

            repo.add(s1);
            repo.add(s2);
            repo.add(s3);

            if (repo.size() == 3) System.out.println("[OK] add & size");
            else System.out.println("[FAIL] add & size");

            Student f = repo.findByIdentity("ben@mail.com");
            if (f != null && f.email().equals("ben@mail.com")) System.out.println("[OK] findByIdentity");
            else System.out.println("[FAIL] findByIdentity");

            List<Student> all = repo.getAll();
            if (all.size() == 3) System.out.println("[OK] getAll returns correct size");
            else System.out.println("[FAIL] getAll size");

            boolean removed = repo.removeByIdentity("anna@mail.com");
            if (removed && repo.size() == 2 && repo.findByIdentity("anna@mail.com") == null)
                System.out.println("[OK] removeByIdentity");
            else System.out.println("[FAIL] removeByIdentity");

            try {
                repo.add(s2); // s2 already present
                System.out.println("[FAIL] duplicate add allowed");
            } catch (IllegalArgumentException e) {
                System.out.println("[OK] duplicate add prevented");
            }

            boolean rem2 = repo.remove(s3);
            if (rem2 && repo.size() == 1) System.out.println("[OK] remove(element)");
            else System.out.println("[FAIL] remove(element)");

        } catch (Exception e) {
            System.out.println("[FAIL] Unexpected exception in RepositoryTests: " + e.getMessage());
            log.severe("RepositoryTests unexpected exception: " + e.getMessage());
        }
    }
}

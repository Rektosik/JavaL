package ua.test;

import org.junit.jupiter.api.Test;
import ua.model.Student;
import ua.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortingTests {

    @Test
    void testSortByLastName() {
        StudentRepository repo = new StudentRepository();
        repo.add(new Student("Bob", "Zeta", "b@mail.com", LocalDate.now().plusDays(2)));
        repo.add(new Student("Alice", "Alpha", "a@mail.com", LocalDate.now().plusDays(2)));

        List<Student> sorted = repo.sortByLastName();

        assertEquals("Alpha", sorted.get(0).lastName());
        assertEquals("Zeta", sorted.get(1).lastName());
    }

    @Test
    void testSortByIdentityDesc() {
        StudentRepository repo = new StudentRepository();
        repo.add(new Student("Bob", "Zeta", "b@mail.com", LocalDate.now().plusDays(2)));
        repo.add(new Student("Alice", "Alpha", "a@mail.com", LocalDate.now().plusDays(2)));

        repo.sortByIdentity("DESC");
        assertEquals("b@mail.com", repo.getAll().get(0).email());
    }
}

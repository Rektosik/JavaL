package ua.repository;

import ua.model.Student;
import ua.util.Utils;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentRepository extends GenericRepository<Student> {
    private static final Logger LOG = Utils.getLogger();
    public StudentRepository() {
        super(Student::email);
    }
    public List<Student> sortByLastName() {
        List<Student> list = getAll();
        list.sort(Student.BY_LASTNAME);
        LOG.info("sortByLastName");
        return list;
    }
    public List<Student> sortByEnrollmentDate() {
        List<Student> list = getAll();
        list.sort(Student.BY_DATE);
        LOG.info("sortByEnrollmentDate");
        return list;
    }
    public List<Student> findByLastName(String lastName) {
        LOG.info("findByLastName " + lastName);
        return getAll().stream().filter(s -> s.lastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
    }
    public List<Student> findByLastNamePrefix(String prefix) {
        LOG.info("findByLastNamePrefix " + prefix);
        return getAll().stream().filter(s -> s.lastName().toLowerCase().startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }
    public List<Student> findByEnrollmentBetween(LocalDate fromInclusive, LocalDate toInclusive) {
        LOG.info("findByEnrollmentBetween " + fromInclusive + " - " + toInclusive);
        return getAll().stream().filter(s -> !s.enrollmentDate().isBefore(fromInclusive) && !s.enrollmentDate().isAfter(toInclusive)).collect(Collectors.toList());
    }
    public List<String> getEmailsCollect() {
        LOG.info("getEmailsCollect");
        return getAll().stream().map(Student::email).collect(Collectors.toList());
    }
    public void printAllForEach() {
        LOG.info("printAllForEach");
        getAll().stream().forEach(System.out::println);
    }
    public Optional<String> emailsReduce() {
        LOG.info("emailsReduce");
        return getAll().stream().map(Student::email).reduce((a,b) -> a + ";" + b);
    }
    public long countByLastNameSequential(String lastName) {
        LOG.info("countByLastNameSequential " + lastName);
        return getAll().stream().filter(s -> s.lastName().equalsIgnoreCase(lastName)).count();
    }
    public long countByLastNameParallel(String lastName) {
        LOG.info("countByLastNameParallel " + lastName);
        return getAll().parallelStream().filter(s -> s.lastName().equalsIgnoreCase(lastName)).count();
    }
    public static List<Student> flattenListsOfStudents(Stream<Collection<Student>> lists) {
        return lists.flatMap(Collection::stream).collect(Collectors.toList());
    }
}

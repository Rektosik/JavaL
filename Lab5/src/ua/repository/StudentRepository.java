package ua.repository;

import ua.model.Student;
import java.util.Comparator;
import java.util.List;

public class StudentRepository extends GenericRepository<Student> {

    public StudentRepository() {
        super(Student::email);
    }

    public List<Student> sortByLastName() {
        List<Student> list = getAll();
        list.sort(Student.BY_LASTNAME);
        return list;
    }

    public List<Student> sortByEnrollmentDate() {
        List<Student> list = getAll();
        list.sort(Student.BY_DATE);
        return list;
    }
}

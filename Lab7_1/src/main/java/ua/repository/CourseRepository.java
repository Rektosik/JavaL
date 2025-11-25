package ua.repository;

import ua.model.Course;
import ua.util.Utils;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CourseRepository extends GenericRepository<Course> {
    private static final Logger LOG = Utils.getLogger();
    public CourseRepository() {
        super(Course::title);
    }
    public List<Course> sortByCredits() {
        List<Course> list = getAll();
        list.sort(Course.BY_CREDITS);
        LOG.info("sortByCredits size=" + list.size());
        return list;
    }
    public List<Course> findByTitle(String title) {
        LOG.info("findByTitle " + title);
        return getAll().stream().filter(c -> c.title().equalsIgnoreCase(title)).collect(Collectors.toList());
    }
    public List<Course> findByCreditsRange(int minInclusive, int maxInclusive) {
        LOG.info("findByCreditsRange " + minInclusive + "-" + maxInclusive);
        return getAll().stream().filter(c -> c.credits() >= minInclusive && c.credits() <= maxInclusive).collect(Collectors.toList());
    }
    public List<String> collectStudentEmailsFromCourses() {
        LOG.info("collectStudentEmailsFromCourses");
        return getAll().stream().flatMap(c -> c.students().stream()).map(s -> s.email()).distinct().collect(Collectors.toList());
    }
}

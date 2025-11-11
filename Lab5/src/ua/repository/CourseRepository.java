package ua.repository;

import ua.model.Course;
import java.util.List;

public class CourseRepository extends GenericRepository<Course> {

    public CourseRepository() {
        super(Course::title);
    }

    public List<Course> sortByCredits() {
        List<Course> list = getAll();
        list.sort(Course.BY_CREDITS);
        return list;
    }
}

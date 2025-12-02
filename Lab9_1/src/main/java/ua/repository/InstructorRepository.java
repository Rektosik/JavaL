package ua.repository;

import ua.model.Instructor;
import ua.util.Utils;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InstructorRepository extends GenericRepository<Instructor> {
    private static final Logger LOG = Utils.getLogger();
    public InstructorRepository() {
        super(Instructor::lastName);
    }
    public List<Instructor> sortByLastName() {
        List<Instructor> list = getAll();
        list.sort(Instructor.BY_LASTNAME);
        LOG.info("sortByLastName size=" + list.size());
        return list;
    }
    public List<Instructor> findByExpertise(String expertise) {
        LOG.info("findByExpertise " + expertise);
        String ex = expertise == null ? "" : expertise.toLowerCase();
        return getAll().stream().filter(i -> i.expertise().toLowerCase().contains(ex)).collect(Collectors.toList());
    }
    public long countByExpertise(String expertise) {
        LOG.info("countByExpertise " + expertise);
        String ex = expertise == null ? "" : expertise.toLowerCase();
        return getAll().stream().filter(i -> i.expertise().toLowerCase().contains(ex)).count();
    }
}
package ua;

import org.junit.jupiter.api.*;
import ua.model.Student;
import ua.repository.StudentRepository;
import ua.util.DataSerializationException;
import ua.util.SerializerService;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationTests {

    private StudentRepository repo;

    @BeforeEach
    void init() {
        repo = new StudentRepository();
        repo.add(new Student("Test", "User", "test@mail.com", LocalDate.now().plusDays(1).toString()));
    }

    @Test
    void testJsonSerialization() {
        String path = "test_data.json";

        SerializerService.saveToJson(path, repo.getAll());
        List<Student> list = SerializerService.loadJson(path, Student.class);

        assertEquals(1, list.size(), "List size should be 1");
        assertEquals("test@mail.com", list.get(0).email(), "Email should match");

        new File(path).delete();
    }

    @Test
    void testYamlSerialization() {
        String path = "test_data.yaml";

        SerializerService.saveToYaml(path, repo.getAll());
        List<Student> list = SerializerService.loadYaml(path, Student.class);

        assertEquals(1, list.size(), "List size should be 1");
        assertEquals("test@mail.com", list.get(0).email(), "Email should match");

        new File(path).delete();
    }

    @Test
    void testInvalidJsonThrowsException() {
        assertThrows(DataSerializationException.class, () ->
                SerializerService.loadJson("missing_file.json", Student.class)
        );
    }
}
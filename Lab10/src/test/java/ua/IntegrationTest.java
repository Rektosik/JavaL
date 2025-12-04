package ua;

import org.junit.jupiter.api.Test;
import ua.model.Student;
import ua.repository.StudentRepository;
import ua.util.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void testParallelFileLoading() throws IOException, ExecutionException, InterruptedException {
        Path tempFile = Path.of("test_students.csv");
        String content = "Ivan,Ivanov,i@test.com,2023-09-01\nPetro,Petrov,p@test.com,2023-09-01";
        Files.writeString(tempFile, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        StudentRepository repo = new StudentRepository();

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            List<String[]> data = Utils.readDataFromFile("test_students.csv");
            data.parallelStream().forEach(parts -> {
                try {
                    repo.add(new Student(parts[0], parts[1], parts[2], parts[3]));
                } catch (Exception e) {
                }
            });
        });

        future.get();

        assertEquals(2, repo.size(), "Should load 2 students");
        assertNotNull(repo.findByIdentity("i@test.com"));

        Files.deleteIfExists(tempFile);
    }
}
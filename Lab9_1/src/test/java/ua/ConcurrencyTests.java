package ua;

import org.junit.jupiter.api.Test;
import ua.model.Student;
import ua.repository.StudentRepository;
import ua.service.AsyncProcessingService;

import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrencyTests {

    @Test
    void testThreadSafeRepositoryAdding() throws InterruptedException {
        StudentRepository repo = new StudentRepository();
        int threads = 10;
        int itemsPerThread = 100;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                for (int j = 0; j < itemsPerThread; j++) {
                    repo.add(new Student(
                            "N" + j,
                            "L" + threadId + "_" + j,
                            "u" + threadId + "_" + j + "@test.com",
                            LocalDate.now().toString()
                    ));
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(threads * itemsPerThread, repo.size(), "Repository should contain all items added concurrently");
    }

    @Test
    void testAsyncProcessingResultsMatch() {
        StudentRepository repo = new StudentRepository();
        IntStream.range(0, 100).forEach(i ->
                repo.add(new Student("A", "Target" + i, "t" + i + "@m.c", "2025-01-01")));
        IntStream.range(0, 100).forEach(i ->
                repo.add(new Student("B", "Other" + i, "o" + i + "@m.c", "2025-01-01")));

        AsyncProcessingService service = new AsyncProcessingService();
        String prefix = "Target";

        long resStream = service.processWithParallelStream(repo, prefix);
        long resExec = service.processWithExecutorService(repo, prefix);
        long resFuture = service.processWithCompletableFuture(repo, prefix).join();

        assertEquals(100, resStream, "ParallelStream result wrong");
        assertEquals(100, resExec, "ExecutorService result wrong");
        assertEquals(100, resFuture, "CompletableFuture result wrong");
    }
}
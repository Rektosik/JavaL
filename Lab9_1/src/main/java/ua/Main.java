package ua;

import ua.model.Student;
import ua.repository.StudentRepository;
import ua.service.AsyncProcessingService;
import ua.util.Utils;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Utils.printSeparator();
        System.out.println("=== MULTITHREADING LAB STARTED ===");
        Utils.printSeparator();

        StudentRepository studentRepo = new StudentRepository();

        System.out.println("\n[1] Parallel Data Loading...");
        long startLoad = System.currentTimeMillis();

        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> generateStudents(studentRepo, "GroupA", 1000));
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> generateStudents(studentRepo, "GroupB", 1000));
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(() -> generateStudents(studentRepo, "GroupC", 1000));

        CompletableFuture<Void> allLoading = CompletableFuture.allOf(task1, task2, task3);

        try {
            allLoading.join();
            long endLoad = System.currentTimeMillis();
            System.out.println("Loaded " + studentRepo.size() + " students in " + (endLoad - startLoad) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n[2] Processing Data Comparison...");
        AsyncProcessingService service = new AsyncProcessingService();

        service.compareApproaches(studentRepo, "GroupB");

        System.out.println("\n[3] CompletableFuture Chaining Demo...");
        CompletableFuture.supplyAsync(() -> {
            Utils.getLogger().info("Fetching data...");
            return studentRepo.size();
        }).thenApply(size -> {
            Utils.getLogger().info("Calculating stats for size: " + size);
            return size * 2;
        }).thenAccept(result -> {
            Utils.getLogger().info("Final result: " + result);
        }).join();

        System.out.println("\n=== FINISHED ===");
    }

    private static void generateStudents(StudentRepository repo, String prefix, int count) {
        Utils.getLogger().info("Generating " + count + " students for " + prefix);
        IntStream.range(0, count).parallel().forEach(i -> {
            try {
                repo.add(new Student(
                        "Name" + i,
                        prefix + "_" + i,
                        prefix.toLowerCase() + i + "@mail.com",
                        LocalDate.now().toString()
                ));
            } catch (Exception e) {
            }
        });
    }
}
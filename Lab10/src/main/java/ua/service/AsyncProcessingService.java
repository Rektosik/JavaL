package ua.service;

import ua.model.Student;
import ua.repository.StudentRepository;
import ua.util.Utils;

import java.util.List;
import java.util.concurrent.*;

public class AsyncProcessingService {

    public void compareApproaches(StudentRepository repo, String lastNamePrefix) {
        Utils.getLogger().info("--- Starting Comparison for prefix: '" + lastNamePrefix + "' ---");

        long startStream = System.nanoTime();
        long countStream = processWithParallelStream(repo, lastNamePrefix);
        long endStream = System.nanoTime();
        Utils.getLogger().info("ParallelStream found: " + countStream + " in " + (endStream - startStream) / 1_000_000 + " ms");

        long startExec = System.nanoTime();
        long countExec = processWithExecutorService(repo, lastNamePrefix);
        long endExec = System.nanoTime();
        Utils.getLogger().info("ExecutorService found: " + countExec + " in " + (endExec - startExec) / 1_000_000 + " ms");

        long startFuture = System.nanoTime();
        long countFuture = processWithCompletableFuture(repo, lastNamePrefix).join();
        long endFuture = System.nanoTime();
        Utils.getLogger().info("CompletableFuture found: " + countFuture + " in " + (endFuture - startFuture) / 1_000_000 + " ms");
    }

    public long processWithParallelStream(StudentRepository repo, String prefix) {
        return repo.getAll().parallelStream()
                .filter(s -> {
                    expensiveOperation();
                    return s.lastName().startsWith(prefix);
                })
                .count();
    }

    public long processWithExecutorService(StudentRepository repo, String prefix) {
        int threads = 4;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Student> allStudents = repo.getAll();
        int chunkSize = allStudents.size() / threads;
        List<Callable<Long>> tasks = new java.util.ArrayList<>();

        for (int i = 0; i < threads; i++) {
            final int start = i * chunkSize;
            final int end = (i == threads - 1) ? allStudents.size() : (i + 1) * chunkSize;
            if (start >= end) continue;

            List<Student> subList = allStudents.subList(start, end);

            tasks.add(() -> {
                Utils.getLogger().info("Task started in thread: " + Thread.currentThread().getName());
                long count = 0;
                for (Student s : subList) {
                    expensiveOperation();
                    if (s.lastName().startsWith(prefix)) count++;
                }
                return count;
            });
        }

        long total = 0;
        try {
            List<Future<Long>> results = executor.invokeAll(tasks);
            for (Future<Long> f : results) {
                total += f.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            Utils.getLogger().severe("Executor error: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
        return total;
    }

    public CompletableFuture<Long> processWithCompletableFuture(StudentRepository repo, String prefix) {
        List<Student> allStudents = repo.getAll();

        CompletableFuture<Long> futureCount = CompletableFuture.supplyAsync(() -> {
            Utils.getLogger().info("Async filtering started in: " + Thread.currentThread().getName());
            return allStudents.stream().filter(s -> {
                expensiveOperation();
                return s.lastName().startsWith(prefix);
            }).count();
        });

        return futureCount.handle((res, ex) -> {
            if (ex != null) {
                Utils.getLogger().severe("Async error: " + ex.getMessage());
                return 0L;
            }
            Utils.getLogger().info("Async filtering finished.");
            return res;
        });
    }

    private void expensiveOperation() {
        try {
            Math.pow(Math.random(), Math.random());
        } catch (Exception e) {
        }
    }
}
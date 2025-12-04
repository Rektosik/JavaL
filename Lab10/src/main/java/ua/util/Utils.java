package ua.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class Utils {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    static {
        try {
            LogManager.getLogManager().reset();
            ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(ch);
            LOGGER.setLevel(Level.INFO);
        } catch (Exception e) {
            System.err.println("Logger init failed: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static String formatDate(LocalDate date) {
        return date == null ? "null" : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static void requireNonEmpty(String value, String fieldName) {
        ValidationHelper.requireNonEmpty(value, fieldName);
    }

    public static void requireEmail(String email) {
        ValidationHelper.requireEmail(email);
    }

    public static void requirePositive(int value, String fieldName) {
        ValidationHelper.requirePositive(value, fieldName);
    }

    public static void requireDateNotPast(LocalDate date, String fieldName) {
        ValidationHelper.requireDateNotPast(date, fieldName);
    }

    public static List<String[]> readDataFromFile(String filePath) {
        List<String[]> records = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            LOGGER.warning("File not found: " + path.toAbsolutePath() + ". Starting with empty list.");
            return records;
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = java.util.Arrays.stream(line.split(","))
                        .map(s -> s.replaceAll("^\"|\"$", "").trim())
                        .toArray(String[]::new);
                if (parts.length >= 4) {
                    records.add(parts);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file: " + filePath, e);
            throw new RuntimeException("Critical error reading file " + filePath, e);
        }
        return records;
    }
}
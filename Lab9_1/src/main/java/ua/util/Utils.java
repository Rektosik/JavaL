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
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;

public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    static {
        try {
            LogManager.getLogManager().reset();
            ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return String.format("[%s] %s: %s%n",
                            Thread.currentThread().getName(),
                            record.getLevel(),
                            record.getMessage());
                }
            });
            LOGGER.addHandler(ch);
            LOGGER.setLevel(Level.INFO);
        } catch (Exception e) {
            System.err.println("Logger init failed: " + e.getMessage());
        }
    }

    public static void printSeparator() {
        System.out.println("=".repeat(60));
    }

    public static String formatDate(LocalDate date) {
        if (date == null) return "null";
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public static List<String[]> readDataFromFile(String filePath) throws InvalidDataException {
        List<String[]> records = new ArrayList<>();
        Path path = Paths.get(filePath);
        LOGGER.info("Reading file: " + path.toAbsolutePath());

        if (!Files.exists(path)) {
            String msg = "File not found: " + path.toAbsolutePath();
            LOGGER.severe(msg);
            throw new InvalidDataException(msg);
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = Arrays.stream(line.split(","))
                        .map(s -> s.replaceAll("^\"|\"$", "").trim())
                        .toArray(String[]::new);
                if (parts.length >= 4) {
                    records.add(parts);
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Error reading file: " + filePath, e);
        }
        return records;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
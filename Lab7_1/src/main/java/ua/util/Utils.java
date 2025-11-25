package ua.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
            FileHandler fh = new FileHandler("app.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Logger init failed: " + e.getMessage());
        }
    }

    public static void printSeparator() {
        System.out.println("=".repeat(40));
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
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] parts = Arrays.stream(line.split(","))
                        .map(s -> s.replaceAll("^\"|\"$", "").trim())
                        .toArray(String[]::new);
                if (parts.length < 4) {
                    LOGGER.warning("Line " + lineNo + " skipped (insufficient fields): " + line);
                    continue;
                }
                boolean anyEmpty = false;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].isBlank()) {
                        LOGGER.warning("Line " + lineNo + " has empty field " + (i + 1) + ": " + line);
                        anyEmpty = true;
                    }
                }
                if (anyEmpty) {
                    LOGGER.info("Skipping line " + lineNo + " due to empty fields.");
                    continue;
                }
                records.add(parts);
            }
            LOGGER.info("File read successfully, records: " + records.size());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "I/O error reading file: " + e.getMessage(), e);
            throw new InvalidDataException("Error reading file: " + filePath, e);
        }

        return records;
    }

    public static void createExampleFile(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) return;
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            bw.write("Alice,Smith,alice@mail.com,2025-10-29\n");
            bw.write("Bob,Brown,bob@mail.com,2025-11-01\n");
            bw.write("Clara,White,clara@mail.com,2025-10-30\n");
            LOGGER.info("Example file created at: " + path.toAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Cannot create example file: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}


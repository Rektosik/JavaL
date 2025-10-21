package ua;

import ua.exception.InvalidDataException;
import ua.io.FileReader;
import ua.model.PersonRecord;
import ua.util.LoggerUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerUtil.getLogger();
        logger.info("App start");
        FileReader reader = new FileReader();
        List<PersonRecord> people = null;

        try {
            Path p = Path.of("src/persons.csv");
            logger.info("Reading file: " + p);
            people = reader.readPersons(p);
            logger.info("Loaded persons: " + people.size());
            for (PersonRecord pr : people) {
                System.out.println(pr);
            }

            boolean testsOk = true;

            try {
                PersonRecord good = new PersonRecord(1, "Test User", "test@mail.com");
                if (good.getId() <= 0) {
                    testsOk = false;
                    logger.warning("Test: created person has invalid id");
                } else {
                    logger.info("Test: create person passed");
                }
            } catch (Exception e) {
                testsOk = false;
                logger.log(Level.WARNING, "Test: create person failed", e);
            }

            try {
                reader.readPersons(Path.of("nonexistent_file.csv"));
                testsOk = false;
                logger.warning("Test: expected FileNotFoundException but none thrown");
            } catch (IOException e) {
                logger.info("Test: file not found handling passed");
            } catch (InvalidDataException e) {
                logger.info("Test: invalid data handling in test passed: " + e.getMessage());
            }

            System.out.println("Basic tests ok: " + testsOk);

        } catch (InvalidDataException e) {
            logger.log(Level.SEVERE, "Invalid data encountered: " + e.getMessage(), e);
            System.err.println("Invalid data in input: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO error: " + e.getMessage(), e);
            System.err.println("IO error: " + e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error: " + e.getMessage(), e);
            System.err.println("Unexpected: " + e.getMessage());
        } finally {
            logger.info("App finished. Items read: " + (people == null ? 0 : people.size()));
        }
    }
}


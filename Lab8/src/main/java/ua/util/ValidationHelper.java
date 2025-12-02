package ua.util;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationHelper {

    public static void checkNonEmpty(String value, String fieldName, List<String> errors) {
        if (value == null || value.isBlank()) {
            errors.add(fieldName + ": cannot be empty");
        }
    }

    public static void checkEmail(String email, List<String> errors) {
        if (email == null || !Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", email)) {
            errors.add("email: invalid format (" + email + ")");
        }
    }

    public static void checkPositive(int value, String fieldName, List<String> errors) {
        if (value <= 0) {
            errors.add(fieldName + ": must be > 0");
        }
    }

    public static void checkNotNull(Object obj, String fieldName, List<String> errors) {
        if (obj == null) {
            errors.add(fieldName + ": cannot be null");
        }
    }

    public static void checkDateNotPast(LocalDate date, String fieldName, List<String> errors) {
        if (date == null) {
            errors.add(fieldName + ": cannot be null");
        } else if (date.isBefore(LocalDate.now())) {
            errors.add(fieldName + ": cannot be in the past (" + date + ")");
        }
    }

    public static void validateAndThrow(List<String> errors, String entityName) {
        if (!errors.isEmpty()) {
            String errorMessage = String.join("; ", errors);
            Utils.getLogger().warning("Validation failed for " + entityName + ": " + errorMessage);
            throw new InvalidDataException(errorMessage);
        } else {
            Utils.getLogger().info("Successfully created/updated " + entityName);
        }
    }


    public static void requireNonEmpty(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
    }

    public static void requireEmail(String email) {
        if (email == null || !Pattern.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$", email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static void requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive");
        }
    }

    public static void requireDateNotPast(LocalDate date, String fieldName) {
        if (date == null) throw new IllegalArgumentException(fieldName + " cannot be null");
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " cannot be in the past");
        }
    }
}
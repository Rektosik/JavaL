package ua.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

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
}

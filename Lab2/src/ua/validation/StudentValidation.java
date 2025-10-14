package ua.validation;

import ua.util.Utils;
import java.time.LocalDate;

public final class StudentValidation {
    private StudentValidation() {}

    public static void requireValidEmail(String email) {
        Utils.requireEmail(email);
    }

    public static void requireDateNotPast(LocalDate date, String fieldName) {
        Utils.requireDateNotPast(date, fieldName);
    }
}

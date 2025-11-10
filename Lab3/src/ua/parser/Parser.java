package ua.parser;

import ua.model.Student;
import ua.util.InvalidDataException;
import ua.util.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Student> parseStudents(String filePath) throws InvalidDataException {
        List<String[]> raw = Utils.readDataFromFile(filePath);
        List<Student> students = new ArrayList<>();

        for (String[] s : raw) {
            try {
                Student st = new Student(
                        s[0].trim(),
                        s[1].trim(),
                        s[2].trim(),
                        LocalDate.parse(s[3].trim())
                );
                students.add(st);
            } catch (Exception e) {
                System.err.println("Invalid student record: " + String.join(",", s));
            }
        }
        return students;
    }
}

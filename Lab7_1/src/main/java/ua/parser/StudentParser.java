package ua.parser;

import ua.model.Student;
import ua.util.InvalidDataException;
import ua.util.Utils;
import java.util.ArrayList;
import java.util.List;

public final class StudentParser {
    private StudentParser() {}

    public static List<Student> parseFromFile(String path) throws InvalidDataException {
        List<String[]> raw = Utils.readDataFromFile(path);
        List<Student> result = new ArrayList<>();

        for (int i = 0; i < raw.size(); i++) {
            String[] parts = raw.get(i);
            try {
                String fn = parts[0].trim();
                String ln = parts[1].trim();
                String em = parts[2].trim();

                String dateStr = parts[3].trim();

                Student s = new Student(fn, ln, em, dateStr);
                result.add(s);
                Utils.getLogger().info("Parsed student: " + s.email());
            } catch (Exception ex) {
                Utils.getLogger().warning("Skipping invalid row #" + (i+1) + " -> " + ex.getMessage());
            }
        }
        Utils.getLogger().info("Parsed students count: " + result.size());
        return result;
    }
}


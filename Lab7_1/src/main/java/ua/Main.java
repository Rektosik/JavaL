package ua;

import ua.model.Student;
import ua.repository.StudentRepository;
import ua.util.SerializerService;
import ua.util.Utils;
import ua.util.DataSerializationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger log = Utils.getLogger();

        System.out.println("=== STARTING APPLICATION ===");

        Properties props = new Properties();
        String jsonPath = "students.json";
        String yamlPath = "students.yaml";
        int generateCount = 5;

        try (var stream = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (stream != null) {
                props.load(stream);
                jsonPath = props.getProperty("json.path", jsonPath);
                yamlPath = props.getProperty("yaml.path", yamlPath);
                generateCount = Integer.parseInt(props.getProperty("generate.count", "5"));
                System.out.println("Config loaded! JSON path: " + jsonPath);
            } else {
                System.out.println("Config not found, using defaults.");
            }
        } catch (IOException e) {
            System.out.println("Error loading config: " + e.getMessage());
        }

        StudentRepository originalRepo = new StudentRepository();
        for (int i = 0; i < generateCount; i++) {
            originalRepo.add(new Student(
                    "Student" + i,
                    "Lastname" + i,
                    "user" + i + "@example.com",
                    LocalDate.now().plusDays(i).toString()
            ));
        }
        System.out.println("Generated " + originalRepo.size() + " students.");

        try {
            System.out.println("--- Saving to files... ---");
            SerializerService.saveToJson(jsonPath, originalRepo.getAll());
            SerializerService.saveToYaml(yamlPath, originalRepo.getAll());
            System.out.println("Save done.");
        } catch (DataSerializationException e) {
            System.out.println("ERROR Saving: " + e.getMessage());
        }

        System.out.println("\n--- Loading from files... ---");
        try {
            List<Student> fromJson = SerializerService.loadJson(jsonPath, Student.class);
            System.out.println("Loaded from JSON: " + fromJson.size() + " items.");

            List<Student> fromYaml = SerializerService.loadYaml(yamlPath, Student.class);
            System.out.println("Loaded from YAML: " + fromYaml.size() + " items.");

            if (fromJson.size() == originalRepo.size()) {
                System.out.println("CHECK JSON: SUCCESS");
            } else {
                System.out.println("CHECK JSON: FAILED");
            }
        } catch (DataSerializationException e) {
            System.out.println("ERROR Loading: " + e.getMessage());
        }

        System.out.println("=== FINISHED ===");
    }
}
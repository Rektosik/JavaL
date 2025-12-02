package ua;

import ua.model.Instructor;
import ua.model.Student;
import ua.repository.InstructorRepository;
import ua.repository.StudentRepository;
import ua.util.InvalidDataException;
import ua.util.SerializerService;
import ua.util.Utils;
import ua.util.DataSerializationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger log = Utils.getLogger();
        System.out.println("=== STARTING APPLICATION ===");

        System.out.println("\n--- 1. Testing Valid Creation ---");
        StudentRepository studentRepo = new StudentRepository();
        try {
            Student validStudent = new Student("John", "Doe", "john.doe@example.com", "2025-09-01");
            studentRepo.add(validStudent);
            System.out.println("Created and saved: " + validStudent);
        } catch (InvalidDataException e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        System.out.println("\n--- 2. Testing Invalid Creation (Expect Exception) ---");
        try {
            Student invalidStudent = new Student("", "", "invalid-email", "");
        } catch (InvalidDataException e) {
            System.out.println("CAUGHT EXPECTED EXCEPTION:");
            System.out.println("Message: " + e.getMessage());
        }

        System.out.println("\n--- 3. Testing Instructor Validation ---");
        InstructorRepository instructorRepo = new InstructorRepository();
        try {
            Instructor invInstructor = new Instructor(null, "", "Math");
        } catch (InvalidDataException e) {
            System.out.println("Instructor Error: " + e.getMessage());
        }

        try {
            Instructor validInst = new Instructor("Alan", "Turing", "Computer Science");
            instructorRepo.add(validInst);
            System.out.println("Instructor saved: " + validInst);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Running Existing Functional ---");
        runOldLogic(studentRepo);

        System.out.println("=== FINISHED ===");
    }

    private static void runOldLogic(StudentRepository repo) {
        Properties props = new Properties();
        String jsonPath = "students.json";
        String yamlPath = "students.yaml";
        int generateCount = 2;

        try (var stream = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (stream != null) {
                props.load(stream);
                jsonPath = props.getProperty("json.path", jsonPath);
                yamlPath = props.getProperty("yaml.path", yamlPath);
            }
        } catch (IOException e) {
            System.out.println("Error config: " + e.getMessage());
        }

        for (int i = 0; i < generateCount; i++) {
            try {
                repo.add(new Student(
                        "GenStudent" + i,
                        "Lastname" + i,
                        "user" + i + "@example.com",
                        LocalDate.now().plusDays(i).toString()
                ));
            } catch (InvalidDataException e) {
                System.err.println("Gen failed: " + e.getMessage());
            }
        }

        try {
            SerializerService.saveToJson(jsonPath, repo.getAll());
            SerializerService.saveToYaml(yamlPath, repo.getAll());
            System.out.println("Saved " + repo.size() + " students to files.");
        } catch (DataSerializationException e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }
}
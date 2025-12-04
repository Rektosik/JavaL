package ua;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class Main {

    private static final String STUDENT_API = "http://localhost:8080/ua/api/students";
    private static final String COURSE_API = "http://localhost:8080/ua/api/courses";

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    public static void main(String[] args) {
        System.out.println("=== CLIENT APP STARTED ===");
        try {
            System.out.println("\n--- 1. Testing Students API ---");
            String jsonStudent = """
                {
                    "firstName": "Client",
                    "lastName": "Saver",
                    "email": "save_me@test.com",
                    "enrollmentDate": "2025-05-20"
                }
                """;
            sendRequest("POST", STUDENT_API, jsonStudent);
            sendRequest("GET", STUDENT_API, null);

            System.out.println("\n--- 2. Testing Courses API ---");
            String jsonCourse = """
                {
                    "title": "Java Advanced",
                    "description": "Deep dive into Java",
                    "credits": 5,
                    "startDate": "2026-09-01",
                    "level": "ADVANCED",
                    "instructor": {
                        "firstName": "Dr",
                        "lastName": "House",
                        "expertise": "Diagnostics"
                    }
                }
                """;
            sendRequest("POST", COURSE_API, jsonCourse);
            sendRequest("GET", COURSE_API, null);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void sendRequest(String method, String url, String body) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url));

        if (body != null) {
            builder.method(method, BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json");
        } else {
            builder.method(method, BodyPublishers.noBody());
        }

        HttpResponse<String> response = CLIENT.send(builder.build(), BodyHandlers.ofString());
        System.out.println(method + " " + url + " -> " + response.statusCode());

        if (response.statusCode() >= 400) {
            System.out.println("Error Body: " + response.body());
        }
    }
}
package ua.model;

public enum CourseLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;

    public String getDescription() {
        return switch (this) {
            case BEGINNER -> "Basic introduction level";
            case INTERMEDIATE -> "Intermediate knowledge required";
            case ADVANCED -> "Advanced and in-depth study";
        };
    }
}

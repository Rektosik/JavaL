package ua.model;

public enum CourseLevel {
    BEGINNER, INTERMEDIATE, ADVANCED;

    @Override
    public String toString() {
        return switch (this) {
            case BEGINNER -> "Beginner Level";
            case INTERMEDIATE -> "Intermediate Level";
            case ADVANCED -> "Advanced Level";
        };
    }
}


package ua.model;

public enum CourseLevel {
    BEGINNER, INTERMEDIATE, ADVANCED;
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
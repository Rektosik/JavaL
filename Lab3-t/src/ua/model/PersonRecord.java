package ua.model;

public class PersonRecord {
    private final int id;
    private final String name;
    private final String email;

    public PersonRecord(int id, String name, String email) {
        if (id <= 0) throw new IllegalArgumentException("id must be positive");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name empty");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email empty");
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + email;
    }
}

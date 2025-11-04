package ua.model;

public abstract class BaseEntity {
    protected final int id;

    protected BaseEntity(int id) {
        this.id = id;
    }

    protected String describeId() {
        return "ID: " + id;
    }

    public int getId() {
        return id;
    }
}

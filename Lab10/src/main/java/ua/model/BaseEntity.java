package ua.model;

public abstract class BaseEntity {
    protected final int id;

    protected BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
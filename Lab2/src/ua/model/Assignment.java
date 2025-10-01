package ua.model;

import ua.util.Utils;
import java.time.LocalDate;

public record Assignment(ua.model.Module module, LocalDate dueDate, int maxPoints) {
    public Assignment {
        if (module == null) throw new IllegalArgumentException("Module cannot be null");
        Utils.requireDateNotPast(dueDate, "Due date");
        Utils.requirePositive(maxPoints, "Max points");
    }

    @Override
    public String toString() {
        return "Assignment for " + module.title() +
                ", due " + Utils.formatDate(dueDate) +
                ", max " + maxPoints;
    }
}

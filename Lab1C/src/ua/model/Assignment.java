package ua.model;

import ua.util.Utils;
import java.time.LocalDate;

public class Assignment {
    private ua.model.Module module;  // ← Вказано повну назву пакета
    private LocalDate dueDate;
    private int maxPoints;

    public Assignment(ua.model.Module module, LocalDate dueDate, int maxPoints) {
        if (module == null) throw new IllegalArgumentException("Module cannot be null");
        Utils.requireDateNotPast(dueDate, "Due date");
        Utils.requirePositive(maxPoints, "Max points");
        this.module = module;
        this.dueDate = dueDate;
        this.maxPoints = maxPoints;
    }

    public ua.model.Module getModule() {
        return module;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    @Override
    public String toString() {
        return "Assignment for " + module.getTitle()
                + ", due " + Utils.formatDate(dueDate)
                + ", max " + maxPoints;
    }
}

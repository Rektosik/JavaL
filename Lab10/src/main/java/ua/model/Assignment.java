package ua.model;

import ua.util.Utils;
import ua.util.ValidationHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record Assignment(Module module, LocalDate dueDate, int maxPoints) {
    public Assignment {
        List<String> errors = new ArrayList<>();

        ValidationHelper.checkNotNull(module, "module", errors);
        ValidationHelper.checkDateNotPast(dueDate, "dueDate", errors);
        ValidationHelper.checkPositive(maxPoints, "maxPoints", errors);

        ValidationHelper.validateAndThrow(errors, "Assignment");
    }

    @Override
    public String toString() {
        String modTitle = (module != null) ? module.title() : "null";
        return "Assignment for " + modTitle + ", due " + Utils.formatDate(dueDate) + ", max " + maxPoints;
    }
}
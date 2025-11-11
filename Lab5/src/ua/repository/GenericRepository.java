package ua.repository;

import ua.util.Utils;
import java.util.*;
import java.util.logging.Logger;

public class GenericRepository<T extends Comparable<T>> {
    private final Map<Object, T> storage = new LinkedHashMap<>();
    private final IdentityExtractor<T> extractor;
    private final Logger logger = Utils.getLogger();

    public GenericRepository(IdentityExtractor<T> extractor) {
        this.extractor = Objects.requireNonNull(extractor);
        logger.info("Repository created.");
    }

    public synchronized void add(T element) {
        Object id = extractor.extractIdentity(element);
        if (storage.containsKey(id)) throw new IllegalArgumentException("Duplicate key!");

        storage.put(id, element);
        logger.info("Added: " + element);
    }

    public synchronized List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    public synchronized void sortByIdentity(String order) {
        logger.info("Sorting by Comparable identity, order = " + order);

        List<T> sorted = getAll();
        sorted.sort(order.equalsIgnoreCase("DESC")
                ? Comparator.reverseOrder()
                : Comparator.naturalOrder()
        );

        storage.clear();
        sorted.forEach(e -> storage.put(extractor.extractIdentity(e), e));
    }
}

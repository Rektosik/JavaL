package ua.repository;

import ua.util.Utils;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class GenericRepository<T extends Comparable<T>> {
    protected final Map<Object, T> storage = new ConcurrentHashMap<>();
    protected final IdentityExtractor<T> extractor;
    protected final Logger logger = Utils.getLogger();

    public GenericRepository(IdentityExtractor<T> extractor) {
        this.extractor = Objects.requireNonNull(extractor);
    }

    public void add(T element) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null");
        Object id = extractor.extractIdentity(element);
        if (id == null) throw new IllegalArgumentException("Identity cannot be null");

        storage.put(id, element);
        logger.info("Repo: Added/Updated element id=" + id);
    }

    public boolean removeByIdentity(Object identity) {
        if (identity == null) return false;
        T removed = storage.remove(identity);
        return removed != null;
    }

    public T findByIdentity(Object identity) {
        return storage.get(identity);
    }

    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    public int size() {
        return storage.size();
    }
}
package ua.repository;

import ua.util.Utils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenericRepository<T> {
    private final Map<Object, T> storage = new LinkedHashMap<>();
    private final IdentityExtractor<T> extractor;
    private final Logger logger = Utils.getLogger();

    public GenericRepository(IdentityExtractor<T> extractor) {
        if (extractor == null) throw new IllegalArgumentException("IdentityExtractor cannot be null");
        this.extractor = extractor;
        logger.info("GenericRepository created for extractor: " + extractor.getClass().getName());
    }

    public synchronized void add(T element) {
        if (element == null) {
            logger.warning("Attempt to add null element");
            throw new IllegalArgumentException("Element cannot be null");
        }
        Object id = extractor.extractIdentity(element);
        if (id == null) {
            logger.warning("Extracted null identity for element: " + element);
            throw new IllegalArgumentException("Identity cannot be null");
        }
        if (storage.containsKey(id)) {
            logger.warning("Duplicate identity on add: " + id);
            throw new IllegalArgumentException("Element with identity already exists: " + id);
        }
        storage.put(id, element);
        logger.info("Added element with id=" + id + " -> " + element);
    }

    public synchronized boolean removeByIdentity(Object identity) {
        if (identity == null) {
            logger.warning("Attempt to remove by null identity");
            return false;
        }
        T removed = storage.remove(identity);
        if (removed != null) {
            logger.info("Removed element with id=" + identity + " -> " + removed);
            return true;
        } else {
            logger.info("No element found to remove with id=" + identity);
            return false;
        }
    }

    public synchronized boolean remove(T element) {
        if (element == null) {
            logger.warning("Attempt to remove null element");
            return false;
        }
        Object id = extractor.extractIdentity(element);
        return removeByIdentity(id);
    }

    public synchronized List<T> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    public synchronized T findByIdentity(Object identity) {
        if (identity == null) return null;
        T found = storage.get(identity);
        logger.fine("findByIdentity(" + identity + ") -> " + found);
        return found;
    }

    public synchronized int size() {
        return storage.size();
    }
}

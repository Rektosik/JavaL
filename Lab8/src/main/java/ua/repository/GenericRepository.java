package ua.repository;

import ua.util.Utils;

import java.util.*;
import java.util.logging.Logger;

public class GenericRepository<T extends Comparable<T>> {
    protected final Map<Object, T> storage = new LinkedHashMap<>();
    protected final IdentityExtractor<T> extractor;
    protected final Logger logger = Utils.getLogger();
    public GenericRepository(IdentityExtractor<T> extractor) {
        this.extractor = Objects.requireNonNull(extractor);
        logger.info("GenericRepository created for extractor");
    }
    public synchronized void add(T element) {
        if (element == null) throw new IllegalArgumentException("Element cannot be null");
        Object id = extractor.extractIdentity(element);
        if (id == null) throw new IllegalArgumentException("Identity cannot be null");
        if (storage.containsKey(id)) throw new IllegalArgumentException("Duplicate identity: " + id);
        storage.put(id, element);
        logger.info("Added element id=" + id);
    }
    public synchronized boolean removeByIdentity(Object identity) {
        if (identity == null) return false;
        T removed = storage.remove(identity);
        if (removed != null) {
            logger.info("Removed identity=" + identity);
            return true;
        }
        return false;
    }
    public synchronized T findByIdentity(Object identity) {
        return storage.get(identity);
    }
    public synchronized List<T> getAll() {
        return new ArrayList<>(storage.values());
    }
    public synchronized int size() {
        return storage.size();
    }
    public synchronized void sortByIdentity(String order) {
        List<T> list = getAll();
        list.sort("DESC".equalsIgnoreCase(order) ? Comparator.reverseOrder() : Comparator.naturalOrder());
        storage.clear();
        list.forEach(e -> storage.put(extractor.extractIdentity(e), e));
        logger.info("sortByIdentity order=" + order);
    }
}

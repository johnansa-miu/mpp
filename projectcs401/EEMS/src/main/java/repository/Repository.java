package main.java.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface for CRUD operations.
 * @param <T> Entity type
 * @param <ID> ID type
 */
public interface Repository<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    T update(T entity);
    boolean deleteById(ID id);
}

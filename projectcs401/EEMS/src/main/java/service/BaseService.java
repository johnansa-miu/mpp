package main.java.service;

import main.java.repository.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Generic base service providing common CRUD operations
 * Following DRY principle to avoid code repetition across service implementations
 * 
 * @param <T> Entity type
 * @param <ID> ID type
 */
public abstract class BaseService<T, ID> {
    protected final Repository<T, ID> repository;

    protected BaseService(Repository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * Save an entity
     * @param entity the entity to save
     * @return the saved entity
     */
    public T save(T entity) {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to save entity", e);
        }
    }

    /**
     * Find entity by ID
     * @param id the entity ID
     * @return Optional containing the entity if found
     */
    public Optional<T> findById(ID id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to find entity by ID: " + id, e);
        }
    }

    /**
     * Find all entities
     * @return list of all entities
     */
    public List<T> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve all entities", e);
        }
    }

    /**
     * Update an entity
     * @param entity the entity to update
     * @return the updated entity
     */
    public T update(T entity) {
        try {
            return repository.update(entity);
        } catch (Exception e) {
            throw new ServiceException("Failed to update entity", e);
        }
    }

    /**
     * Delete entity by ID
     * @param id the entity ID
     * @return true if deleted successfully
     */
    public boolean deleteById(ID id) {
        try {
            return repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to delete entity by ID: " + id, e);
        }
    }

    /**
     * Get entity by ID or throw exception
     * @param id the entity ID
     * @return the entity
     * @throws ServiceException if entity not found
     */
    protected T getByIdOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() -> new ServiceException("Entity not found with ID: " + id));
    }
}


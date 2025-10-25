package main.java.controller;

import main.java.service.BaseService;
import main.java.service.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Abstract base controller providing common CRUD operations
 * 
 * @param <T> Entity type
 * @param <ID> ID type
 */
public abstract class BaseController<T, ID> {
    protected final BaseService<T, ID> service;

    protected BaseController(BaseService<T, ID> service) {
        this.service = service;
    }

    /**
     * Save an entity
     * @param entity the entity to save
     * @return the saved entity
     * @throws ServiceException if save operation fails
     */
    public T save(T entity) {
        return service.save(entity);
    }

    /**
     * Find entity by ID
     * @param id the entity ID
     * @return Optional containing the entity if found
     * @throws ServiceException if operation fails
     */
    public Optional<T> findById(ID id) {
        return service.findById(id);
    }

    /**
     * Find all entities
     * @return list of all entities
     * @throws ServiceException if operation fails
     */
    public List<T> findAll() {
        return service.findAll();
    }

    /**
     * Update an entity
     * @param entity the entity to update
     * @return the updated entity
     * @throws ServiceException if update operation fails
     */
    public T update(T entity) {
        return service.update(entity);
    }

    /**
     * Delete entity by ID
     * @param id the entity ID
     * @return true if deleted successfully
     * @throws ServiceException if delete operation fails
     */
    public boolean deleteById(ID id) {
        return service.deleteById(id);
    }
}


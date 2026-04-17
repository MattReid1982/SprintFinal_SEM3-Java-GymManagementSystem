package com.keyin.gymmanagement.dao;

import java.sql.Connection;

/**
 * Base DAO interface defining common data access operations.
 */
public interface IBaseDAO<T> {
    /**
     * Find all records of type T.
     * 
     * @return list of all records
     */
    java.util.List<T> findAll();

    /**
     * Find a record by its ID.
     * 
     * @param id the ID to search for
     * @return the found record, or null if not found
     */
    T findById(int id);

    /**
     * Create a new record.
     * 
     * @param entity the entity to create
     * @return true if successful, false otherwise
     */
    boolean create(T entity);

    /**
     * Update an existing record.
     * 
     * @param entity the entity to update
     * @return true if successful, false otherwise
     */
    boolean update(T entity);

    /**
     * Delete a record by ID.
     * 
     * @param id the ID of the record to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int id);
}

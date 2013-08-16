package com.mtrubs.dnd.service;

import com.mtrubs.dnd.domain.Entity;
import com.mtrubs.dnd.service.exception.ValidationException;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 9:26 PM
 */
public interface EntityService<T extends Entity> {

    void add(T entity) throws ValidationException;

    T get(long id);

    void delete(long id);

    void update(T entity) throws ValidationException;

    boolean existsByName(String name);

    void close();
}

package com.mtrubs.dnd.manager;

import com.mtrubs.dnd.domain.Entity;

/**
 * User: Matthew
 * Date: 7/31/13
 * Time: 8:49 PM
 */
public interface EntityManager<T extends Entity> {

    void add(T entity);

    T get(long id);

    void delete(long id);

    void update(T entity);

    boolean existsByName(String name);

    void close();
}

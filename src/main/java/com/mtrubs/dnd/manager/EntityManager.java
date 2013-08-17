package com.mtrubs.dnd.manager;

import com.mtrubs.dnd.domain.Entity;

import java.util.List;

/**
 * User: Matthew
 * Date: 7/31/13
 * Time: 8:49 PM
 */
public interface EntityManager<T extends Entity> {

    // TODO: might need some kind of incremental load
    List<T> getAll();

    void add(T entity);

    T get(long id);

    void delete(long id);

    void update(T entity);

    boolean existsByName(String name);

    void close();
}

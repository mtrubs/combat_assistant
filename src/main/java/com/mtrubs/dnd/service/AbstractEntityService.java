package com.mtrubs.dnd.service;

import com.mtrubs.dnd.domain.Entity;
import com.mtrubs.dnd.manager.EntityManager;
import com.mtrubs.dnd.service.exception.ValidationException;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 9:01 PM
 */
public class AbstractEntityService<T extends Entity> implements EntityService<T> {

    private EntityManager<T> dataSource;

    protected AbstractEntityService(EntityManager<T> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(T entity) throws ValidationException {
        this.dataSource.add(entity);
    }

    @Override
    public T get(long id) {
        return this.dataSource.get(id);
    }

    @Override
    public void delete(long id) {
        this.dataSource.delete(id);
    }

    @Override
    public void update(T entity) throws ValidationException {
        this.dataSource.update(entity);
    }

    @Override
    public boolean existsByName(String name) {
        return this.dataSource.existsByName(name);
    }

    @Override
    public void close() {
        this.dataSource.close();
    }
}

package com.mtrubs.dnd.service;

import com.mtrubs.dnd.domain.Race;
import com.mtrubs.dnd.manager.RaceManager;
import com.mtrubs.dnd.service.exception.ValidationException;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 9:08 PM
 */
public class RaceManagerService extends AbstractEntityService<Race> implements RaceService {

    public RaceManagerService(RaceManager dataSource) {
        super(dataSource);
    }

    @Override
    public void add(Race entity) throws ValidationException {
        // TODO: validate
        super.add(entity);
    }

    @Override
    public void update(Race entity) throws ValidationException {
        // TODO: validate
        super.update(entity);
    }
}

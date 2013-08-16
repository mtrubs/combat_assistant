package com.mtrubs.dnd.service;

import com.mtrubs.android.dnd.manager.PlayerClassDataSource;
import com.mtrubs.dnd.domain.PlayerClass;
import com.mtrubs.dnd.service.exception.ValidationException;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 8:59 PM
 */
public class PlayerClassManagerService extends AbstractEntityService<PlayerClass> implements PlayerClassService {

    public PlayerClassManagerService(PlayerClassDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void add(PlayerClass entity) throws ValidationException {
        // TODO: validate
        super.add(entity);
    }

    @Override
    public void update(PlayerClass entity) throws ValidationException {
        // TODO: validate
        super.update(entity);
    }
}

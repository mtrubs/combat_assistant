package com.mtrubs.dnd.service;

import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.manager.AbilityManager;
import com.mtrubs.dnd.service.exception.ValidationException;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 9:09 PM
 */
public class AbilityManagerService extends AbstractEntityService<Ability> implements AbilityService {

    public AbilityManagerService(AbilityManager dataSource) {
        super(dataSource);
    }

    @Override
    public void add(Ability entity) throws ValidationException {
        // TODO: validate
        super.add(entity);
    }

    @Override
    public void update(Ability entity) throws ValidationException {
        // TODO: validate
        super.update(entity);
    }
}

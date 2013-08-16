package com.mtrubs.android.dnd.service;

import android.content.Context;
import com.mtrubs.android.dnd.manager.AbilityDataSource;
import com.mtrubs.dnd.service.AbilityManagerService;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 9:11 PM
 */
public class AbilityDataSourceService extends AbilityManagerService {

    public AbilityDataSourceService(Context context) {
        super(new AbilityDataSource(context));
    }
}

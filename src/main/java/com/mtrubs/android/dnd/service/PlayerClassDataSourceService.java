package com.mtrubs.android.dnd.service;

import android.content.Context;
import com.mtrubs.android.dnd.manager.PlayerClassDataSource;
import com.mtrubs.dnd.service.PlayerClassManagerService;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 9:13 PM
 */
public class PlayerClassDataSourceService extends PlayerClassManagerService {

    public PlayerClassDataSourceService(Context context) {
        super(new PlayerClassDataSource(context));
    }
}

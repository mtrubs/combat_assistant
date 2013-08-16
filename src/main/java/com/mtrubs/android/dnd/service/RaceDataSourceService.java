package com.mtrubs.android.dnd.service;

import android.content.Context;
import com.mtrubs.android.dnd.manager.RaceDataSource;
import com.mtrubs.dnd.service.RaceManagerService;

/**
 * User: Matthew
 * Date: 8/11/13
 * Time: 9:13 PM
 */
public class RaceDataSourceService extends RaceManagerService {

    public RaceDataSourceService(Context context) {
        super(new RaceDataSource(context));
    }
}

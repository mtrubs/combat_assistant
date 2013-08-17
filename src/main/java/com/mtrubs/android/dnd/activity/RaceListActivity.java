package com.mtrubs.android.dnd.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.mtrubs.android.dnd.BuildConfig;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.service.RaceDataSourceService;
import com.mtrubs.android.dnd.widget.RaceAdapter;
import com.mtrubs.dnd.domain.Race;
import com.mtrubs.dnd.mock.RaceCreator;
import com.mtrubs.dnd.service.RaceService;

import java.util.List;

/**
 * User: Matthew
 * Date: 8/8/13
 * Time: 7:07 PM
 */
public class RaceListActivity extends ListActivity {

    private static final String TAG = RaceListActivity.class.getCanonicalName();

    private RaceService raceService;

    private static void logError(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.getClass().getCanonicalName() + "::" + e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.race_list);

            this.raceService = new RaceDataSourceService(this);
            List<Race> races = this.raceService.getAll();

            ListView listView = (ListView) findViewById(android.R.id.list);
            RaceAdapter adapter = new RaceAdapter(this);
            races.add(RaceCreator.create(-1L, getResources().getString(R.string.race_list_create)));
            adapter.updateEntities(races);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            logError(e);
        }
    }

    @Override
    public void onDestroy() {
        if (this.raceService != null) {
            this.raceService.close();
            this.raceService = null;
        }
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, RaceFormActivity.class);
        intent.putExtra(RaceFormActivity.MESSAGE_ID, id);
        startActivity(intent);
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EditMenuActivity.class);
        startActivity(intent);
    }
}

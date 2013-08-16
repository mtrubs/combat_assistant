package com.mtrubs.android.dnd.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.mtrubs.android.dnd.BuildConfig;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.service.PlayerClassDataSourceService;
import com.mtrubs.android.dnd.widget.PlayerClassAdapter;
import com.mtrubs.dnd.domain.PlayerClass;
import com.mtrubs.dnd.mock.PlayerClassCreator;
import com.mtrubs.dnd.service.PlayerClassService;

import java.util.List;

/**
 * User: Matthew
 * Date: 8/8/13
 * Time: 7:07 PM
 */
public class PlayerClassListActivity extends ListActivity implements PlayerClassList {

    // TODO: database
    private List<PlayerClass> playerClasses = PlayerClassCreator.getAll();
    private PlayerClassService playerClassService;

    private static final String TAG = PlayerClassFormActivity.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.player_class_list);

            this.playerClassService = new PlayerClassDataSourceService(this);

            ListView listView = (ListView) findViewById(android.R.id.list);
            PlayerClassAdapter adapter = new PlayerClassAdapter(this, R.layout.player_class_list_item);
            adapter.updateEntities(this.playerClasses);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            logError(e);
        }
    }

    private void logError(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.getClass().getCanonicalName() + "::" + e.getMessage());
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void addEntity(View view) {
        Intent intent = new Intent(this, PlayerClassFormActivity.class);
        startActivity(intent);
    }
}
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
public class PlayerClassListActivity extends ListActivity {

    private static final String TAG = PlayerClassListActivity.class.getCanonicalName();

    private PlayerClassService playerClassService;

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.entity_list);

            this.playerClassService = new PlayerClassDataSourceService(this);
            List<PlayerClass> playerClasses = this.playerClassService.getAll();

            ListView listView = (ListView) findViewById(android.R.id.list);
            PlayerClassAdapter adapter = new PlayerClassAdapter(this);
            playerClasses.add(PlayerClassCreator.create(-1L, getResources().getString(R.string.playerClass_list_create)));
            adapter.updateEntities(playerClasses);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            logError(e);
        }
    }

    @Override
    public void onDestroy() {
        if (this.playerClassService != null) {
            this.playerClassService.close();
            this.playerClassService = null;
        }
        super.onDestroy();
    }

    private static void logError(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.getClass().getCanonicalName() + "::" + e.getMessage());
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this, PlayerClassFormActivity.class);
        intent.putExtra(PlayerClassFormActivity.MESSAGE_ID, id);
        startActivity(intent);
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EditMenuActivity.class);
        startActivity(intent);
    }
}

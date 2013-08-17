package com.mtrubs.android.dnd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.mtrubs.android.dnd.BuildConfig;
import com.mtrubs.android.dnd.R;

/**
 * User: Matthew
 * Date: 8/16/13
 * Time: 7:46 PM
 */
public class EditMenuActivity extends Activity implements EditMenu {

    private static final String TAG = EditMenuActivity.class.getCanonicalName();

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.edit_menu);
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
    public void launchAbilityList(View view) {
        Intent intent = new Intent(this, AbilityListActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchPlayerClassList(View view) {
        Intent intent = new Intent(this, PlayerClassListActivity.class);
        startActivity(intent);
    }

    @Override
    public void launchRaceList(View view) {
        // TODO: Activity
    }
}

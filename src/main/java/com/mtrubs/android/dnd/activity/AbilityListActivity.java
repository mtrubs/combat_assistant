package com.mtrubs.android.dnd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.mtrubs.android.dnd.BuildConfig;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.fragment.AbilityList;
import com.mtrubs.android.dnd.fragment.AbilityListListener;
import com.mtrubs.android.dnd.service.AbilityDataSourceService;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.service.AbilityService;

import java.util.List;

/**
 * User: Matthew
 * Date: 8/16/13
 * Time: 8:57 PM
 */
public class AbilityListActivity extends Activity implements AbilityListListener {

    private static final String TAG = AbilityListActivity.class.getCanonicalName();

    private AbilityService abilityService;

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.ability_list_activity);

            this.abilityService = new AbilityDataSourceService(this);

            AbilityList abilityList = (AbilityList) getFragmentManager().findFragmentById(R.id.ability_list);

            List<Ability> abilities = this.abilityService.getAll();
            abilities.add(abilityList.createPlaceholder(getResources().getString(R.string.ability_list_create)));
            abilityList.updateAbilities(abilities);
        } catch (Exception e) {
            logError(e);
        }
    }

    @Override
    public void onDestroy() {
        if (this.abilityService != null) {
            this.abilityService.close();
            this.abilityService = null;
        }
        super.onDestroy();
    }

    private static void logError(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.getClass().getCanonicalName() + "::" + e.getMessage());
        }
    }

    @Override
    public void onAbilityItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, AbilityFormActivity.class);
        intent.putExtra(AbilityFormActivity.MESSAGE_ID, id);
        startActivity(intent);
    }

    @SuppressWarnings("RefusedBequest")
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, EditMenuActivity.class);
        startActivity(intent);
    }
}

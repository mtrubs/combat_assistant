package com.mtrubs.android.dnd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.mtrubs.android.dnd.BuildConfig;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.fragment.AbilityList;
import com.mtrubs.android.dnd.fragment.AbilityListListener;
import com.mtrubs.android.dnd.service.AbilityDataSourceService;
import com.mtrubs.android.dnd.service.RaceDataSourceService;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.Race;
import com.mtrubs.dnd.mock.AbilityCreator;
import com.mtrubs.dnd.service.AbilityService;
import com.mtrubs.dnd.service.RaceService;
import com.mtrubs.dnd.service.exception.DuplicateEntityException;
import com.mtrubs.dnd.service.exception.InvalidNameException;
import com.mtrubs.dnd.service.exception.ValidationException;
import com.mtrubs.util.ReflectionUtils;

import java.util.List;

/**
 * User: Matthew
 * Date: 8/10/13
 * Time: 2:35 PM
 */
public class RaceFormActivity extends Activity implements RaceForm, AbilityListListener {

    public static final String MESSAGE_ID = "mtrubs_id";
    private static final String TAG = RaceFormActivity.class.getCanonicalName();

    private RaceService raceService;
    private AbilityService abilityService;

    private long raceId;

    private static void logError(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.getClass().getCanonicalName() + "::" + e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.race_form);

            this.raceService = new RaceDataSourceService(this);
            this.abilityService = new AbilityDataSourceService(this);

            AbilityList abilityList = (AbilityList) getFragmentManager().findFragmentById(R.id.ability_list);
            // TODO: abilities associated with this race
            List<Ability> abilities = AbilityCreator.getAll();
            abilities.add(abilityList.createPlaceholder(getResources().getString(R.string.ability_list_assign)));
            abilityList.updateAbilities(abilities);

            // check if this is an edit vs create
            this.raceId = getIntent().getLongExtra(MESSAGE_ID, -1L);
            if (isEdit()) {
                // if this is edit mode then we load the data and populate the form
                Race race = this.raceService.get(this.raceId);
                TextView nameElement = (TextView) findViewById(R.id.race_name);
                nameElement.setText(race.getName());
            } else {
                // cannot delete the item if we are in create mode
                View view = findViewById(R.id.delete);
                view.setVisibility(View.GONE);
            }
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
        if (this.abilityService != null) {
            this.abilityService.close();
            this.abilityService = null;
        }
        super.onDestroy();
    }

    @Override
    public void save(View view) {
        TextView nameElement = (TextView) findViewById(R.id.race_name);
        String name = nameElement.getText().toString();

        try {
            if (isEdit()) {
                Race race = this.raceService.get(this.raceId);
                ReflectionUtils.setProperty(race, "name", name);
                this.raceService.update(race);
            } else {
                Race race = new Race();
                ReflectionUtils.setProperty(race, "name", name);
                this.raceService.add(race);
            }

            returnToList();
        } catch (DuplicateEntityException e) {
            // TODO: strings.xml
            nameElement.setError("A race by this name already exists");
        } catch (InvalidNameException e) {
            // TODO: strings.xml
            nameElement.setError("Name must be [A-Za-z0-9 ]* and must not be empty");
        } catch (ValidationException e) {
            logError(e);
        }
    }

    @Override
    public void cancel(View view) {
        returnToList();
    }

    @Override
    public void delete(View view) {
        AlertDialog.Builder confirmation = new AlertDialog.Builder(this);
        confirmation.setTitle(getResources().getString(R.string.race_form_delete_title));
        confirmation.setMessage(getResources().getString(R.string.race_form_delete_message));
        confirmation.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
        confirmation.setPositiveButton(
                getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                        dialog.dismiss();
                    }
                });
        confirmation.setNegativeButton(
                getResources().getString(R.string.no),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        confirmation.show();
    }

    private boolean isEdit() {
        return this.raceId > 0L;
    }

    private void delete() {
        this.raceService.delete(this.raceId);
        returnToList();
    }

    private void returnToList() {
        Intent intent = new Intent(this, RaceListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAbilityItemClick(ListView l, View v, int position, long id) {
        // TODO: implement
    }
}

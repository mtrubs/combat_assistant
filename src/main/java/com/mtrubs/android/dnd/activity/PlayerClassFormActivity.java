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
import com.mtrubs.android.dnd.service.PlayerClassDataSourceService;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.PlayerClass;
import com.mtrubs.dnd.mock.AbilityCreator;
import com.mtrubs.dnd.service.AbilityService;
import com.mtrubs.dnd.service.PlayerClassService;
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
public class PlayerClassFormActivity extends Activity implements PlayerClassForm, AbilityListListener {

    public static final String MESSAGE_ID = "mtrubs_id";
    private static final String TAG = PlayerClassFormActivity.class.getCanonicalName();

    private PlayerClassService playerClassService;
    private AbilityService abilityService;

    private long playerClassId;

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.player_class_form);

            this.playerClassService = new PlayerClassDataSourceService(this);
            this.abilityService = new AbilityDataSourceService(this);

            AbilityList abilityList = (AbilityList) getFragmentManager().findFragmentById(R.id.ability_list);
            // TODO: Abilities associated with this class
            List<Ability> abilities = AbilityCreator.getAll();
            abilities.add(abilityList.createPlaceholder(getResources().getString(R.string.ability_list_assign)));
            abilityList.updateAbilities(abilities);

            // check if this is an edit vs create
            this.playerClassId = getIntent().getLongExtra(MESSAGE_ID, -1L);
            if (isEdit()) {
                // if this is edit mode then we load the data and populate the form
                PlayerClass playerClass = this.playerClassService.get(this.playerClassId);
                TextView nameElement = (TextView) findViewById(R.id.playerClass_name);
                nameElement.setText(playerClass.getName());
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
        if (this.playerClassService != null) {
            this.playerClassService.close();
            this.playerClassService = null;
        }
        if (this.abilityService != null) {
            this.abilityService.close();
            this.abilityService = null;
        }
        super.onDestroy();
    }

    @Override
    public void save(View view) {
        TextView nameElement = (TextView) findViewById(R.id.playerClass_name);
        String name = nameElement.getText().toString();

        try {
            if (isEdit()) {
                PlayerClass playerClass = this.playerClassService.get(this.playerClassId);
                ReflectionUtils.setProperty(playerClass, "name", name);
                this.playerClassService.update(playerClass);
            } else {
                PlayerClass playerClass = new PlayerClass();
                ReflectionUtils.setProperty(playerClass, "name", name);
                this.playerClassService.add(playerClass);
            }

            returnToList();
        } catch (DuplicateEntityException e) {
            // TODO: strings.xml
            nameElement.setError("A class by this name already exists");
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
        confirmation.setTitle(getResources().getString(R.string.playerClass_form_delete_title));
        confirmation.setMessage(getResources().getString(R.string.playerClass_form_delete_message));
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

    private static void logError(Exception e) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, e.getClass().getCanonicalName() + "::" + e.getMessage());
        }
    }

    private boolean isEdit() {
        return this.playerClassId > 0L;
    }

    private void delete() {
        this.playerClassService.delete(this.playerClassId);
        returnToList();
    }

    private void returnToList() {
        Intent intent = new Intent(this, PlayerClassListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAbilityItemClick(ListView l, View v, int position, long id) {
        // TODO: implement
    }
}

package com.mtrubs.android.dnd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.mtrubs.android.dnd.BuildConfig;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.service.AbilityDataSourceService;
import com.mtrubs.android.dnd.view.ViewHolder;
import com.mtrubs.android.dnd.widget.EnumSpinnerAdapter;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.AbilityType;
import com.mtrubs.dnd.service.AbilityService;
import com.mtrubs.dnd.service.exception.DuplicateEntityException;
import com.mtrubs.dnd.service.exception.InvalidNameException;
import com.mtrubs.dnd.service.exception.ValidationException;
import com.mtrubs.util.ArrayUtils;
import com.mtrubs.util.ReflectionUtils;

/**
 * User: Matthew
 * Date: 8/16/13
 * Time: 9:14 PM
 */
public class AbilityFormActivity extends Activity implements PlayerClassForm {

    public static final String MESSAGE_ID = "mtrubs_id";
    private static final String TAG = AbilityFormActivity.class.getCanonicalName();

    private AbilityService abilityService;

    private long abilityId;

    private static final AbilityType[] ABILITY_TYPES;
    private static final int[] ABILITY_TYPE_RESOURCES;

    static {
        ABILITY_TYPES = new AbilityType[3];
        ABILITY_TYPE_RESOURCES = new int[3];

        // TODO: what to do with the Feature types?
        addAbility(0, AbilityType.AtWill, R.string.ability_type_atWill);
        //addAbility(3, AbilityType.AtWillFeature, R.string.ability_type_atWill_feature);
        addAbility(1, AbilityType.Encounter, R.string.ability_type_encounter);
        //addAbility(4, AbilityType.EncounterFeature, R.string.ability_type_encounter_feature);
        addAbility(2, AbilityType.Daily, R.string.ability_type_daily);
        //addAbility(5, AbilityType.DailyFeature, R.string.ability_type_daily_feature);
    }

    private static void addAbility(int index, AbilityType type, int resourceId) {
        ABILITY_TYPES[index] = type;
        ABILITY_TYPE_RESOURCES[index] = resourceId;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        try {
            super.onCreate(savedInstance);
            setContentView(R.layout.ability_form);

            this.abilityService = new AbilityDataSourceService(this);
            Spinner typeElement = (Spinner) findViewById(R.id.ability_type);
            typeElement.setAdapter(new EnumSpinnerAdapter<AbilityType>(this, R.layout.ability_type_list_item, ABILITY_TYPES, ABILITY_TYPE_RESOURCES) {

                @Override
                protected void createView(int position, View convertView, ViewGroup parent) {
                    TextView name = ViewHolder.get(convertView, R.id.ability_name);
                    name.setText(getItemResource(position));
                }
            });

            // check if this is an edit vs create
            this.abilityId = getIntent().getLongExtra(MESSAGE_ID, -1L);
            if (isEdit()) {
                // if this is edit mode then we load the data and populate the form
                Ability ability = this.abilityService.get(this.abilityId);
                TextView nameElement = (TextView) findViewById(R.id.ability_name);
                nameElement.setText(ability.getName());
                typeElement.setSelection(ArrayUtils.indexOf(ABILITY_TYPES, ability.getType()));
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

    private boolean isEdit() {
        return this.abilityId > 0L;
    }

    @Override
    public void save(View view) {
        TextView nameElement = (TextView) findViewById(R.id.ability_name);
        String name = nameElement.getText().toString();

        Spinner typeElement = (Spinner) findViewById(R.id.ability_type);
        AbilityType type = ABILITY_TYPES[typeElement.getSelectedItemPosition()];

        try {
            if (isEdit()) {
                Ability ability = this.abilityService.get(this.abilityId);
                ReflectionUtils.setProperty(ability, "name", name);
                ReflectionUtils.setProperty(ability, "type", type);
                this.abilityService.update(ability);
            } else {
                Ability ability = new Ability();
                ReflectionUtils.setProperty(ability, "name", name);
                ReflectionUtils.setProperty(ability, "type", type);
                this.abilityService.add(ability);
            }

            returnToList();
        } catch (DuplicateEntityException e) {
            // TODO: strings.xml
            nameElement.setError("An ability by this name already exists");
        } catch (InvalidNameException e) {
            // TODO: strings.xml
            nameElement.setError("Name must be [A-Za-z0-9 ]* and must not be empty");
        } catch (ValidationException e) {
            logError(e);
        }
    }

    @Override
    public void delete(View view) {
        AlertDialog.Builder confirmation = new AlertDialog.Builder(this);
        confirmation.setTitle(getResources().getString(R.string.ability_form_delete_title));
        confirmation.setMessage(getResources().getString(R.string.ability_form_delete_message));
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

    private void delete() {
        this.abilityService.delete(this.abilityId);
        returnToList();
    }

    @Override
    public void cancel(View view) {
        returnToList();
    }

    private void returnToList() {
        Intent intent = new Intent(this, AbilityListActivity.class);
        startActivity(intent);
    }
}

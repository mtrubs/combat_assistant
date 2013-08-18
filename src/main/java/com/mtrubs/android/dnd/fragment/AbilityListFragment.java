package com.mtrubs.android.dnd.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.widget.AbilityAdapter;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.AbilityType;
import com.mtrubs.dnd.mock.AbilityCreator;

import java.util.List;

/**
 * User: Matthew
 * Date: 8/12/13
 * Time: 8:49 PM
 */
public class AbilityListFragment extends ListFragment implements AbilityList {

    private AbilityListListener listener;

    @SuppressWarnings("RefusedBequest")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.entity_list, container);
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        ListAdapter adapter = new AbilityAdapter(getActivity());
        setListAdapter(adapter);
    }

    @Override
    public void updateAbilities(List<Ability> abilities) {
        ((AbilityAdapter) this.getListAdapter()).updateEntities(abilities);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AbilityListListener) {
            this.listener = (AbilityListListener) activity;
        } else {
            throw new ClassCastException(activity.getClass().getName() + " must implement AbilityListListener");
        }
    }

    @Override
    public void onDetach() {
        this.listener = null;
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        this.listener.onAbilityItemClick(l, v, position, id);
    }

    @Override
    public Ability createPlaceholder(String name) {
        return AbilityCreator.create(-1L, name, AbilityType.None);
    }
}

package com.mtrubs.android.dnd.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.view.ViewHolder;
import com.mtrubs.dnd.domain.Ability;

/**
 * User: Matthew
 * Date: 8/12/13
 * Time: 9:28 PM
 */
public class AbilityAdapter extends EntityListAdaptor<Ability> {

    public AbilityAdapter(Context context) {
        super(context, R.layout.ability_list_item);
    }

    @Override
    protected void createView(Ability entity, View convertView, ViewGroup parent) {
        TextView name = ViewHolder.get(convertView, R.id.ability_name);
        name.setText(entity.getName());
    }
}

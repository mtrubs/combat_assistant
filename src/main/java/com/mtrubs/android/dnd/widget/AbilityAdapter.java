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

    public AbilityAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    protected void createView(Ability entity, View convertView, ViewGroup parent) {
        TextView name = ViewHolder.get(convertView, R.id.playerClass_name);
        name.setText(entity.getName());
    }
}

package com.mtrubs.android.dnd.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.view.ViewHolder;
import com.mtrubs.dnd.domain.PlayerClass;

/**
 * User: Matthew
 * Date: 8/10/13
 * Time: 9:05 AM
 */
public class PlayerClassAdapter extends EntityListAdaptor<PlayerClass> {

    public PlayerClassAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    protected void createView(PlayerClass entity, View convertView, ViewGroup parent) {
        TextView name = ViewHolder.get(convertView, R.id.playerClass_name);
        name.setText(entity.getName());
    }
}

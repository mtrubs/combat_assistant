package com.mtrubs.android.dnd.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mtrubs.android.dnd.R;
import com.mtrubs.android.dnd.view.ViewHolder;
import com.mtrubs.dnd.domain.PlayerClass;
import com.mtrubs.dnd.domain.Race;

/**
 * User: Matthew
 * Date: 8/10/13
 * Time: 9:05 AM
 */
public class RaceAdapter extends EntityListAdaptor<Race> {

    public RaceAdapter(Context context) {
        super(context, R.layout.race_list_item);
    }

    @Override
    protected void createView(Race entity, View convertView, ViewGroup parent) {
        TextView name = ViewHolder.get(convertView, R.id.race_name);
        name.setText(entity.getName());
    }
}

package com.mtrubs.android.dnd.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.mtrubs.dnd.domain.Entity;

import java.util.Collections;
import java.util.List;

/**
 * User: Matthew
 * Date: 8/10/13
 * Time: 8:46 AM
 */
public abstract class EntityListAdaptor<T extends Entity> extends BaseAdapter {

    private final Context context;
    private final int resource;

    private List<T> entities = Collections.emptyList();

    protected EntityListAdaptor(Context context, int resource) {
        this.context = context;
        this.resource = resource;
    }

    public void updateEntities(List<T> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.entities.size();
    }

    @Override
    public T getItem(int position) {
        return this.entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        }
        createView(getItem(position), convertView, parent);
        return convertView;
    }

    protected abstract void createView(T entity, View convertView, ViewGroup parent);
}

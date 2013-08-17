package com.mtrubs.android.dnd.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * User: Matthew
 * Date: 8/17/13
 * Time: 8:54 AM
 */
public abstract class EnumSpinnerAdapter<T extends Enum<T>> extends BaseAdapter {

    private final Context context;
    private final int resource;
    private final T[] items;
    private final int[] resourceIds;

    protected EnumSpinnerAdapter(Context context, int resource, T[] items, int[] resourceIds) {
        this.context = context;
        this.resource = resource;
        this.items = items;
        this.resourceIds = resourceIds;
    }

    @Override
    public int getCount() {
        return this.items.length;
    }

    @Override
    public T getItem(int position) {
        return this.items[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).ordinal();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(this.resource, parent, false);
        }
        createView(position, convertView, parent);
        return convertView;
    }

    protected String getItemResource(int position) {
        return this.context.getResources().getString(this.resourceIds[position]);
    }

    protected abstract void createView(int position, View convertView, ViewGroup parent);
}

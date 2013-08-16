package com.mtrubs.android.dnd.view;

import android.util.SparseArray;
import android.view.View;

/**
 * User: Matthew
 * Date: 8/10/13
 * Time: 8:59 AM
 */
public class ViewHolder {

    public static <T extends View> T get(View view, int id) {
        // noinspection unchecked
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        // noinspection unchecked
        return (T) childView;
    }
}

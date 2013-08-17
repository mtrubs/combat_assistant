package com.mtrubs.android.dnd.activity;

import android.view.View;

/**
 * This represents the interface between the view and the activity for the
 * race form.
 * <p/>
 * User: Matthew
 * Date: 8/17/13
 * Time: 4:32 PM
 */
public interface RaceForm {

    void save(View view);

    void delete(View view);

    void cancel(View view);
}

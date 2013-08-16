package com.mtrubs.android.dnd.fragment;

import android.view.View;
import android.widget.ListView;

/**
 * User: Matthew
 * Date: 8/12/13
 * Time: 9:21 PM
 */
public interface AbilityListListener {

    void onAbilityItemClick(ListView l, View v, int position, long id);
}

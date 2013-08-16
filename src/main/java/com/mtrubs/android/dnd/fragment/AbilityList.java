package com.mtrubs.android.dnd.fragment;

import android.view.View;
import com.mtrubs.dnd.domain.Ability;

import java.util.List;

/**
 * User: Matthew
 * Date: 8/12/13
 * Time: 9:35 PM
 */
public interface AbilityList {

    void updateAbilities(List<Ability> abilities);

    Ability createPlaceholder(String name);
}

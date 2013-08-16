package com.mtrubs.dnd.mock;

import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.AbilityType;
import com.mtrubs.dnd.domain.PlayerClass;
import com.mtrubs.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: Matthew
 * Date: 8/14/13
 * Time: 8:25 PM
 */
public class AbilityCreator {

    private static List<Ability> ALL;

    private static Comparator<Ability> COMPARATOR = new Comparator<Ability>() {
        @Override
        public int compare(Ability a, Ability b) {
            return a.getName().compareTo(b.getName());
        }
    };

    static {
        ALL = new ArrayList<Ability>();
        // Cleric - At Wills
        ALL.add(create(1, "Lance of Faith", AbilityType.AtWill));
        ALL.add(create(2, "Priest's Shield", AbilityType.AtWill));
        ALL.add(create(3, "Righteous Brand", AbilityType.AtWill));
        ALL.add(create(4, "Sacred Flame", AbilityType.AtWill));
        // Cleric - Level 1 - Encounter Prayers
        ALL.add(create(5, "Cause Fear", AbilityType.Encounter));
        ALL.add(create(6, "Divine Glow", AbilityType.Encounter));
        ALL.add(create(7, "Healing Strike", AbilityType.Encounter));
        ALL.add(create(8, "Wrathful Thunder", AbilityType.Encounter));
        // Cleric - Level 1 - Daily Prayers
        ALL.add(create(9, "Avenging Flame", AbilityType.Daily));
        ALL.add(create(10, "Beacon of Home", AbilityType.Daily));
        ALL.add(create(11, "Cascade of Light", AbilityType.Daily));
        ALL.add(create(12, "Guardian of Faith", AbilityType.Daily));

        Collections.sort(ALL, COMPARATOR);
    }

    private AbilityCreator() {
    }

    public static Ability create(long id, String name, AbilityType type) {
        Ability ability = new Ability();
        ReflectionUtils.setProperty(ability, "id", id);
        ReflectionUtils.setProperty(ability, "name", name);
        ReflectionUtils.setProperty(ability, "type", type);
        return ability;
    }

    public static List<Ability> getAll() {
        return new ArrayList<Ability>(ALL);
    }
}

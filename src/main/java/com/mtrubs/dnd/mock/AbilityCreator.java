package com.mtrubs.dnd.mock;

import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.AbilityType;
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
        ALL.add(create(1L, "Lance of Faith", AbilityType.AtWill));
        ALL.add(create(2L, "Priest's Shield", AbilityType.AtWill));
        ALL.add(create(3L, "Righteous Brand", AbilityType.AtWill));
        ALL.add(create(4L, "Sacred Flame", AbilityType.AtWill));
        // Cleric - Level 1 - Encounter Prayers
        ALL.add(create(5L, "Cause Fear", AbilityType.Encounter));
        ALL.add(create(6L, "Divine Glow", AbilityType.Encounter));
        ALL.add(create(7L, "Healing Strike", AbilityType.Encounter));
        ALL.add(create(8L, "Wrathful Thunder", AbilityType.Encounter));
        // Cleric - Level 1 - Daily Prayers
        ALL.add(create(9L, "Avenging Flame", AbilityType.Daily));
        ALL.add(create(10L, "Beacon of Home", AbilityType.Daily));
        ALL.add(create(11L, "Cascade of Light", AbilityType.Daily));
        ALL.add(create(12L, "Guardian of Faith", AbilityType.Daily));

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

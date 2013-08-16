package com.mtrubs.dnd.mock;

import com.mtrubs.dnd.domain.PlayerClass;
import com.mtrubs.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: Matthew
 * Date: 8/8/13
 * Time: 8:29 PM
 */
public class PlayerClassCreator {

    private static List<PlayerClass> ALL;

    private static Comparator<PlayerClass> COMPARATOR = new Comparator<PlayerClass>() {
        @Override
        public int compare(PlayerClass a, PlayerClass b) {
            return a.getName().compareTo(b.getName());
        }
    };

    static {
        ALL = new ArrayList<PlayerClass>();
        // PHB
        ALL.add(create(1, "Cleric"));
        ALL.add(create(2, "Fighter"));
        ALL.add(create(3, "Paladin"));
        ALL.add(create(4, "Ranger"));
        ALL.add(create(5, "Rogue"));
        ALL.add(create(6, "Warlock"));
        ALL.add(create(7, "Warlord"));
        ALL.add(create(8, "Wizard"));
        // PHB2
        ALL.add(create(9, "Avenger"));
        ALL.add(create(10, "Barbarian"));
        ALL.add(create(11, "Bard"));
        ALL.add(create(12, "Druid"));
        ALL.add(create(13, "Invoker"));
        ALL.add(create(14, "Shaman"));
        ALL.add(create(15, "Sorcerer"));
        ALL.add(create(16, "Warden"));
        // PHB 3
        ALL.add(create(17, "Ardent"));
        ALL.add(create(18, "Battlemind"));
        ALL.add(create(19, "Monk"));
        ALL.add(create(20, "Psion"));
        ALL.add(create(21, "Runepriest"));
        ALL.add(create(22, "Seeker"));

        Collections.sort(ALL, COMPARATOR);
    }

    public static List<PlayerClass> getAll() {
        return new ArrayList<PlayerClass>(ALL);
    }

    public static PlayerClass create(int id, String name) {
        PlayerClass playerClass = new PlayerClass();
        ReflectionUtils.setProperty(playerClass, "name", name);
        ReflectionUtils.setProperty(playerClass, "id", id);
        return playerClass;
    }
}

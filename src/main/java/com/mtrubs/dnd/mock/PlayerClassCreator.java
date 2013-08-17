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

    private static final List<PlayerClass> ALL;

    private static final Comparator<PlayerClass> COMPARATOR = new Comparator<PlayerClass>() {
        @Override
        public int compare(PlayerClass a, PlayerClass b) {
            return a.getName().compareTo(b.getName());
        }
    };

    static {
        ALL = new ArrayList<PlayerClass>();
        // PHB
        ALL.add(create(1L, "Cleric"));
        ALL.add(create(2L, "Fighter"));
        ALL.add(create(3L, "Paladin"));
        ALL.add(create(4L, "Ranger"));
        ALL.add(create(5L, "Rogue"));
        ALL.add(create(6L, "Warlock"));
        ALL.add(create(7L, "Warlord"));
        ALL.add(create(8L, "Wizard"));
        // PHB2
        ALL.add(create(9L, "Avenger"));
        ALL.add(create(10L, "Barbarian"));
        ALL.add(create(11L, "Bard"));
        ALL.add(create(12L, "Druid"));
        ALL.add(create(13L, "Invoker"));
        ALL.add(create(14L, "Shaman"));
        ALL.add(create(15L, "Sorcerer"));
        ALL.add(create(16L, "Warden"));
        // PHB 3
        ALL.add(create(17L, "Ardent"));
        ALL.add(create(18L, "Battlemind"));
        ALL.add(create(19L, "Monk"));
        ALL.add(create(20L, "Psion"));
        ALL.add(create(21L, "Runepriest"));
        ALL.add(create(22L, "Seeker"));

        Collections.sort(ALL, COMPARATOR);
    }

    private PlayerClassCreator() {
    }

    public static List<PlayerClass> getAll() {
        return new ArrayList<PlayerClass>(ALL);
    }

    public static PlayerClass create(long id, String name) {
        PlayerClass playerClass = new PlayerClass();
        ReflectionUtils.setProperty(playerClass, "name", name);
        ReflectionUtils.setProperty(playerClass, "id", id);
        return playerClass;
    }
}

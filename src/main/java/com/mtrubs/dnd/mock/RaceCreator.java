package com.mtrubs.dnd.mock;

import com.mtrubs.dnd.domain.Race;
import com.mtrubs.util.ReflectionUtils;

import java.util.*;

/**
 * User: Matthew
 * Date: 8/8/13
 * Time: 8:31 PM
 */
public class RaceCreator {

    private static final List<Race> ALL;

    private static final Comparator<Race> COMPARATOR = new Comparator<Race>() {
        @Override
        public int compare(Race a, Race b) {
            return a.getName().compareTo(b.getName());
        }
    };

    static {
        ALL = new ArrayList<Race>();
        ALL.add(create(1L, "Dragonborn"));
        ALL.add(create(2L, "Dwarf"));
        ALL.add(create(3L, "Eladrin"));
        ALL.add(create(4L, "Elf"));
        ALL.add(create(5L, "Half-Elf"));
        ALL.add(create(6L, "Halfling"));
        ALL.add(create(7L, "Human"));
        ALL.add(create(8L, "Tiefling"));

        Collections.sort(ALL, COMPARATOR);
    }

    private RaceCreator() {
    }

    public static Collection<Race> getAll() {
        return ALL;
    }

    public static Race create(long id, String name) {
        Race race = new Race();
        ReflectionUtils.setProperty(race, "name", name);
        ReflectionUtils.setProperty(race, "id", id);
        return race;
    }
}

package com.mtrubs.dnd.mock;

import com.mtrubs.dnd.domain.Race;
import com.mtrubs.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Matthew
 * Date: 8/8/13
 * Time: 8:31 PM
 */
public class RaceCreator {

    public static Collection<Race> getAll() {
        Collection<Race> results = new ArrayList<Race>();
        results.add(create(1L, "Dragonborn"));
        results.add(create(2L, "Dwarf"));
        results.add(create(3L, "Eladrin"));
        results.add(create(4L, "Elf"));
        results.add(create(5L, "Half-Elf"));
        results.add(create(6L, "Halfling"));
        results.add(create(7L, "Human"));
        results.add(create(8L, "Tiefling"));
        return results;
    }

    public static Race create(long id, String name) {
        Race race = new Race();
        ReflectionUtils.setProperty(race, "name", name);
        ReflectionUtils.setProperty(race, "id", id);
        return race;
    }
}

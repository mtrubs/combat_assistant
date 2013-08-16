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
        results.add(create(1, "Dragonborn"));
        results.add(create(2, "Dwarf"));
        results.add(create(3, "Eladrin"));
        results.add(create(4, "Elf"));
        results.add(create(5, "Half-Elf"));
        results.add(create(6, "Halfling"));
        results.add(create(7, "Human"));
        results.add(create(8, "Tiefling"));
        return results;
    }

    public static Race create(int id, String name) {
        Race race = new Race();
        ReflectionUtils.setProperty(race, "name", name);
        ReflectionUtils.setProperty(race, "id", id);
        return race;
    }
}

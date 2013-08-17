package com.mtrubs.android.dnd.manager;

import android.app.Activity;
import android.content.Context;
import com.mtrubs.dnd.domain.Race;
import com.mtrubs.test.IntegrationTest;
import com.mtrubs.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 * User: Matthew
 * Date: 8/1/13
 * Time: 7:57 PM
 */
@Category(IntegrationTest.class)
@RunWith(RobolectricTestRunner.class)
public class RaceDataSourceTest {

    private RaceDataSource dataSource;

    @Before
    public void create() {
        Context c = Robolectric.buildActivity(Activity.class).get();
        this.dataSource = new RaceDataSource(c);
    }

    @After
    public void destroy() {
        this.dataSource.close();
    }

    @Test
    public void add() {
        Race entity1 = create("Human");
        this.dataSource.add(entity1);
        assertTrue(entity1.getId() > 0);

        Race entity2 = create("Dwarf");
        this.dataSource.add(entity2);
        assertTrue(entity2.getId() > 0);

        assertNotEquals(entity1.getId(), entity2.getId());
    }

    @Test
    public void delete() {
        Race entity1 = add("Human");
        Race entity2 = add("Dwarf");

        assertNotNull(this.dataSource.get(entity1.getId()));
        assertNotNull(this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity2.getId() + 1L)); // non-existent

        this.dataSource.delete(entity1.getId());

        assertNull(this.dataSource.get(entity1.getId()));
        assertNotNull(this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity2.getId() + 1L)); // non-existent
    }

    @Test
    public void get() {
        Race entity1 = add("Human");
        Race entity2 = add("Dwarf");

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity2.getId() + 1L)); // non-existent
    }

    @Test
    public void getAll() {
        List<Race> all = this.dataSource.getAll();
        assertNotNull(all);
        assertEquals(0, all.size());

        Race entity1 = add("Human");
        Race entity2 = add("Dwarf");

        all = this.dataSource.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());
        assertEntity(entity1, all.get(0));
        assertEntity(entity2, all.get(1));
    }

    @Test
    public void update() {
        Race entity1 = add("Human");
        Race entity2 = add("Dwarf");
        Race entity3 = create("Gnome");
        setId(entity3, entity2.getId() + 1L); // non-existent

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent

        setName(entity1, entity1.getName() + "_MOD");
        this.dataSource.update(entity1);

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent

        this.dataSource.update(entity3);

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent
    }

    private static void assertEntity(Race expected, Race actual) {
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEntity(
                    expected.getId(),
                    expected.getName(),
                    actual
            );
        }
    }

    private static void assertEntity(long expectedId, String expectedName, Race actual) {
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
        assertEquals(expectedName, actual.getName());
    }

    private Race add(String name) {
        Race entity = create(name);
        this.dataSource.add(entity);
        assumeTrue(entity.getId() > 0);
        return entity;
    }

    private static Race create(String name) {
        Race entity = new Race();
        setName(entity, name);
        return entity;
    }

    private static void setId(Race entity, long value) {
        ReflectionUtils.setProperty(entity, "id", value);
    }

    private static void setName(Race entity, String value) {
        ReflectionUtils.setProperty(entity, "name", value);
    }
}

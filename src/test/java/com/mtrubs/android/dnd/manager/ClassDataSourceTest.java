package com.mtrubs.android.dnd.manager;

import android.app.Activity;
import android.content.Context;
import com.mtrubs.dnd.domain.PlayerClass;
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
 * Date: 8/4/13
 * Time: 5:32 PM
 */
@Category(IntegrationTest.class)
@RunWith(RobolectricTestRunner.class)
public class ClassDataSourceTest {

    private PlayerClassDataSource dataSource;

    @Before
    public void create() {
        Context c = Robolectric.buildActivity(Activity.class).get();
        this.dataSource = new PlayerClassDataSource(c);
    }

    @After
    public void destroy() {
        this.dataSource.close();
    }

    @Test
    public void add() {
        PlayerClass entity1 = create("Fighter");
        this.dataSource.add(entity1);
        assertTrue(entity1.getId() > 0);

        PlayerClass entity2 = create("Rogue");
        this.dataSource.add(entity2);
        assertTrue(entity2.getId() > 0);

        assertNotEquals(entity1.getId(), entity2.getId());
    }

    @Test
    public void delete() {
        PlayerClass entity1 = add("Fighter");
        PlayerClass entity2 = add("Rogue");

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
        PlayerClass entity1 = add("Fighter");
        PlayerClass entity2 = add("Rogue");

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity2.getId() + 1L)); // non-existent
    }

    @Test
    public void getAll() {
        List<PlayerClass> all = this.dataSource.getAll();
        assertNotNull(all);
        assertEquals(0, all.size());

        PlayerClass entity1 = add("Fighter");
        PlayerClass entity2 = add("Rogue");

        all = this.dataSource.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());
        assertEntity(entity1, all.get(0));
        assertEntity(entity2, all.get(1));
    }

    @Test
    public void update() {
        PlayerClass entity1 = add("Fighter");
        PlayerClass entity2 = add("Rogue");
        PlayerClass entity3 = create("Cleric");
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

    private static void assertEntity(PlayerClass expected, PlayerClass actual) {
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

    private static void assertEntity(long expectedId, String expectedName, PlayerClass actual) {
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
        assertEquals(expectedName, actual.getName());
    }

    private PlayerClass add(String name) {
        PlayerClass entity = create(name);
        this.dataSource.add(entity);
        assumeTrue(entity.getId() > 0);
        return entity;
    }

    private static PlayerClass create(String name) {
        PlayerClass entity = new PlayerClass();
        setName(entity, name);
        return entity;
    }

    private static void setId(PlayerClass entity, long value) {
        ReflectionUtils.setProperty(entity, "id", value);
    }

    private static void setName(PlayerClass entity, String value) {
        ReflectionUtils.setProperty(entity, "name", value);
    }
}

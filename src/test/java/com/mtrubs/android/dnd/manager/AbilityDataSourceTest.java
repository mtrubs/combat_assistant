package com.mtrubs.android.dnd.manager;

import android.app.Activity;
import android.content.Context;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.AbilityType;
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
 * Date: 8/5/13
 * Time: 8:47 PM
 */
@Category(IntegrationTest.class)
@RunWith(RobolectricTestRunner.class)
public class AbilityDataSourceTest {

    private AbilityDataSource dataSource;

    @Before
    public void create() {
        Context c = Robolectric.buildActivity(Activity.class).get();
        this.dataSource = new AbilityDataSource(c);
    }

    @After
    public void destroy() {
        this.dataSource.close();
    }

    @Test
    public void add() {
        Ability entity1 = create("Lance of Faith", AbilityType.AtWill);
        this.dataSource.add(entity1);
        assertTrue(entity1.getId() > 0);

        Ability entity2 = create("\"Divine's Glow\"", AbilityType.Encounter);
        this.dataSource.add(entity2);
        assertTrue(entity2.getId() > 0);

        assertNotEquals(entity1.getId(), entity2.getId());
    }

    @Test
    public void delete() {
        Ability entity1 = add("Lance of Faith", AbilityType.AtWill);
        Ability entity2 = add("Divine Glow", AbilityType.Encounter);

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
        Ability entity1 = add("Lance of Faith", AbilityType.AtWill);
        Ability entity2 = add("Divine Glow", AbilityType.Encounter);

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity2.getId() + 1L)); // non-existent
    }

    @Test
    public void getAll() {
        List<Ability> all = this.dataSource.getAll();
        assertNotNull(all);
        assertEquals(0, all.size());

        Ability entity1 = add("Lance of Faith", AbilityType.AtWill);
        Ability entity2 = add("Divine Glow", AbilityType.Encounter);

        all = this.dataSource.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());
        assertEntity(entity1, all.get(0));
        assertEntity(entity2, all.get(1));
    }

    @Test
    public void update() {
        Ability entity1 = add("Lance of Faith", AbilityType.AtWill);
        Ability entity2 = add("Divine Glow", AbilityType.Encounter);
        Ability entity3 = create("Avenging Flame", AbilityType.Daily);
        setId(entity3, entity2.getId() + 1L); // non-existent

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent

        setName(entity1, entity1.getName() + "_MOD");
        setType(entity1, AbilityType.AtWillFeature);
        this.dataSource.update(entity1);

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent

        this.dataSource.update(entity3);

        assertEntity(entity1, this.dataSource.get(entity1.getId()));
        assertEntity(entity2, this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent
    }

    @Test
    public void exists() {
        assertFalse(this.dataSource.existsByName("unknown"));

        add("Lance's \"Faith\"", AbilityType.AtWill);
        assertTrue(this.dataSource.existsByName("Lance's \"Faith\""));
        assertFalse(this.dataSource.existsByName("\"Faith\""));
        assertFalse(this.dataSource.existsByName("Lance"));
        assertFalse(this.dataSource.existsByName("unknown"));
    }

    private static void assertEntity(Ability expected, Ability actual) {
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEntity(
                    expected.getId(),
                    expected.getName(),
                    expected.getType(),
                    actual
            );
        }
    }

    private static void assertEntity(long expectedId, String expectedName, AbilityType expectedType, Ability actual) {
        assertNotNull(actual);
        assertEquals(expectedId, actual.getId());
        assertEquals(expectedName, actual.getName());
        assertEquals(expectedType, actual.getType());
    }

    private Ability add(String name, AbilityType type) {
        Ability entity = create(name, type);
        this.dataSource.add(entity);
        assumeTrue(entity.getId() > 0);
        return entity;
    }

    private static Ability create(String name, AbilityType type) {
        Ability entity = new Ability();
        setName(entity, name);
        setType(entity, type);
        return entity;
    }

    private static void setId(Ability entity, long value) {
        ReflectionUtils.setProperty(entity, "id", value);
    }

    private static void setName(Ability entity, String value) {
        ReflectionUtils.setProperty(entity, "name", value);
    }

    private static void setType(Ability entity, AbilityType value) {
        ReflectionUtils.setProperty(entity, "type", value);
    }
}

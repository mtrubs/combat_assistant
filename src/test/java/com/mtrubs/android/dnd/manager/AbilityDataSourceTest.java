package com.mtrubs.android.dnd.manager;

import android.app.Activity;
import android.content.Context;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.AbilityType;
import com.mtrubs.test.IntegrationTest;
import com.mtrubs.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

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

        assertAbility(entity1.getId(), entity1.getName(), entity1.getType(), this.dataSource.get(entity1.getId()));
        assertAbility(entity2.getId(), entity2.getName(), entity2.getType(), this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity2.getId() + 1L)); // non-existent
    }

    @Test
    public void update() {
        Ability entity1 = add("Lance of Faith", AbilityType.AtWill);
        Ability entity2 = add("Divine Glow", AbilityType.Encounter);
        Ability entity3 = create("Avenging Flame", AbilityType.Daily);
        setId(entity3, entity2.getId() + 1L); // non-existent

        assertAbility(entity1.getId(), entity1.getName(), entity1.getType(), this.dataSource.get(entity1.getId()));
        assertAbility(entity2.getId(), entity2.getName(), entity2.getType(), this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent

        setName(entity1, entity1.getName() + "_MOD");
        setType(entity1, AbilityType.AtWillFeature);
        this.dataSource.update(entity1);

        assertAbility(entity1.getId(), entity1.getName(), entity1.getType(), this.dataSource.get(entity1.getId()));
        assertAbility(entity2.getId(), entity2.getName(), entity2.getType(), this.dataSource.get(entity2.getId()));
        assertNull(this.dataSource.get(entity3.getId())); // non-existent

        this.dataSource.update(entity3);

        assertAbility(entity1.getId(), entity1.getName(), entity1.getType(), this.dataSource.get(entity1.getId()));
        assertAbility(entity2.getId(), entity2.getName(), entity2.getType(), this.dataSource.get(entity2.getId()));
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

    private static void assertAbility(long expectedId, String expectedName, AbilityType expectedType, Ability actual) {
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

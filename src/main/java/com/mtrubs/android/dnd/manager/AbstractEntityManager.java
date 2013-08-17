package com.mtrubs.android.dnd.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.mtrubs.dnd.domain.Entity;
import com.mtrubs.dnd.manager.EntityManager;
import com.mtrubs.util.ReflectionUtils;
import com.mtrubs.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Matthew
 * Date: 8/4/13
 * Time: 4:19 PM
 */
public abstract class AbstractEntityManager<T extends Entity> implements EntityManager<T> {

    private static final String EQUALS = "=";
    private static final String EQUALS_PARAM = "=?";

    private final DatabaseManager databaseManager;
    private final String databaseTable;
    private final String idColumn;
    private final String nameColumn;

    public AbstractEntityManager(Context context, String databaseTable, String idColumn, String nameColumn) {
        this.databaseManager = new DatabaseManager(context);
        this.databaseTable = databaseTable;
        this.idColumn = idColumn;
        this.nameColumn = nameColumn;
    }

    @Override
    public void add(T entity) {
        // TODO: if object is null?
        // TODO: if id already exists on object?
        // TODO: if object already exists in database?
        // TODO: transaction?
        SQLiteDatabase database = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            toContent(values, entity);
            long id = database.insert(this.databaseTable, null, values);
            setProperty(entity, this.idColumn, id);
        } finally {
            database.close();
        }
    }

    protected void setProperty(T entity, String fieldName, Object value) {
        ReflectionUtils.setProperty(entity, fieldName, value);
    }

    @Override
    public List<T> getAll() {
        SQLiteDatabase database = getReadableDatabase();
        List<T> results = new ArrayList<T>();
        Cursor cursor = null;
        try {
            cursor = database.query(this.databaseTable, getSelect(), null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    results.add(fromCursor(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return results;
    }

    @Override
    public T get(long id) {
        // TODO: if id does not exist in database?
        SQLiteDatabase database = getReadableDatabase();
        T entity = null;
        Cursor cursor = null;
        try {
            cursor = database.query(
                    this.databaseTable,
                    getSelect(),
                    this.idColumn + EQUALS + id,
                    null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                entity = fromCursor(cursor);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return entity;
    }

    @Override
    public void delete(long id) {
        // TODO: if id does not exists in database?
        // TODO: transaction?
        SQLiteDatabase database = getWritableDatabase();
        try {
            database.delete(
                    this.databaseTable,
                    this.idColumn + EQUALS + id,
                    null
            );
        } finally {
            database.close();
        }
    }

    @Override
    public void update(T entity) {
        // TODO: if object is null?
        // TODO: if id does not exist on object?
        // TODO: if id does not exist in database?
        // TODO: transaction?
        SQLiteDatabase database = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            toContent(values, entity);
            database.update(
                    this.databaseTable,
                    values,
                    this.idColumn + EQUALS + entity.getId(),
                    null
            );
        } finally {
            database.close();
        }
    }

    public boolean existsByName(String name) {
        // TODO: if name is null?
        SQLiteDatabase database = getReadableDatabase();
        boolean result = false;
        Cursor cursor = null;
        try {
            name = StringUtils.replace(name, "'", "''"); // TODO: WTF
            cursor = database.query(
                    this.databaseTable,
                    getSelect(),
                    this.nameColumn + EQUALS_PARAM,
                    new String[]{name}, null, null, null);
            result = cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return result;
    }

    public void close() {
        this.databaseManager.close();
    }

    protected SQLiteDatabase getWritableDatabase() {
        return this.databaseManager.getWritableDatabase();
    }

    protected SQLiteDatabase getReadableDatabase() {
        return this.databaseManager.getReadableDatabase();
    }

    /**
     * Return an array of all the columns needed to populate and entity with its data.
     *
     * @return the list of columns to select out of the database.
     */
    protected abstract String[] getSelect();

    /**
     * This is responsible for translating the entity into a ContentValues object for
     * insert/update in the database.
     *
     * @param values the map for the entity.
     * @param entity the entity to translate.
     */
    protected abstract void toContent(ContentValues values, T entity);

    /**
     * This is responsible for translating a full select from the database into the
     * entity object.
     *
     * @param cursor the database data.
     * @return the instantiated entity.
     */
    protected abstract T fromCursor(Cursor cursor);
}

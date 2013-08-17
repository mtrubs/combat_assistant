package com.mtrubs.android.dnd.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.mtrubs.dnd.domain.Ability;
import com.mtrubs.dnd.domain.AbilityType;
import com.mtrubs.dnd.manager.AbilityManager;
import com.mtrubs.util.EnumUtils;

/**
 * User: Matthew
 * Date: 8/5/13
 * Time: 8:08 PM
 */
public class AbilityDataSource extends AbstractEntityManager<Ability> implements AbilityManager {

    private static final String TABLE = "Ability";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";

    private static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_TYPE + " TEXT NOT NULL" +
                    ")";
    private static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE;

    public AbilityDataSource(Context context) {
        super(context, TABLE, COLUMN_ID, COLUMN_NAME);
    }

    @Override
    protected String[] getSelect() {
        return new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_TYPE};
    }

    @Override
    protected void toContent(ContentValues values, Ability entity) {
        values.put(COLUMN_NAME, entity.getName());
        values.put(COLUMN_TYPE, entity.getType().name());
    }

    @Override
    protected Ability fromCursor(Cursor cursor) {
        Ability entity = new Ability();
        setProperty(entity, COLUMN_ID,
                cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        );
        setProperty(entity, COLUMN_NAME,
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        );
        setProperty(entity, COLUMN_TYPE,
                EnumUtils.valueOf(AbilityType.class, cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)))
        );
        return entity;
    }

    protected static String getCreateStatement() {
        return CREATE_STATEMENT;
    }

    protected static String getDropStatement() {
        return DROP_STATEMENT;
    }
}

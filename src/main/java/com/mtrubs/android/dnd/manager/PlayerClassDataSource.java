package com.mtrubs.android.dnd.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.mtrubs.dnd.domain.PlayerClass;
import com.mtrubs.dnd.manager.PlayerClassManager;

/**
 * User: Matthew
 * Date: 8/4/13
 * Time: 4:17 PM
 */
public class PlayerClassDataSource extends AbstractEntityManager<PlayerClass> implements PlayerClassManager {

    private static final String TABLE = "Class";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL" +
                    ")";
    private static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE;

    public PlayerClassDataSource(Context context) {
        super(context, TABLE, COLUMN_ID, COLUMN_NAME);
    }

    @Override
    protected String[] getSelect() {
        return new String[]{COLUMN_ID, COLUMN_NAME};
    }

    @Override
    protected void toContent(ContentValues values, PlayerClass entity) {
        values.put(COLUMN_NAME, entity.getName());
    }

    @Override
    protected PlayerClass fromCursor(Cursor cursor) {
        PlayerClass entity = new PlayerClass();
        setProperty(entity, COLUMN_ID,
                cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        );
        setProperty(entity, COLUMN_NAME,
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
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

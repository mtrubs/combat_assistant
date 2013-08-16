package com.mtrubs.android.dnd.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.mtrubs.dnd.domain.Race;
import com.mtrubs.dnd.manager.RaceManager;

/**
 * User: Matthew
 * Date: 7/31/13
 * Time: 8:32 PM
 */
public class RaceDataSource extends AbstractEntityManager<Race> implements RaceManager {

    private static final String TABLE = "Race";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";

    private static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL" +
                    ")";

    public RaceDataSource(Context context) {
        super(context, TABLE, COLUMN_ID, COLUMN_NAME);
    }

    protected String[] getSelect() {
        return new String[]{COLUMN_ID, COLUMN_NAME};
    }

    @Override
    protected void toContent(ContentValues values, Race entity) {
        values.put(COLUMN_NAME, entity.getName());
    }

    @Override
    protected Race fromCursor(Cursor cursor) {
        Race entity = new Race();
        setProperty(entity, COLUMN_ID,
                cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        );
        setProperty(entity, COLUMN_NAME,
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
        );
        return entity;
    }

    public static String getCreateStatement() {
        return CREATE_STATEMENT;
    }
}

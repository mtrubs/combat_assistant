package com.mtrubs.android.dnd.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Matthew
 * Date: 7/31/13
 * Time: 8:14 PM
 */
public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mtrubs";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Collection<String> statements = new ArrayList<String>();

        statements.add(RaceDataSource.getCreateStatement());
        statements.add(PlayerClassDataSource.getCreateStatement());
        statements.add(AbilityDataSource.getCreateStatement());

        for (String statement : statements) {
            db.execSQL(statement);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: when needed
    }
}

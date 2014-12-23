package com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vmarinov on 11/20/2014.
 */
public abstract class BaseSQLiteOpenHelper
        extends SQLiteOpenHelper {
    public String tableName;

    private static int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "main.db";
    /* NB: Never set the _id column explicitly as SQLite is taking care of it automatically trough
     * the "int primary key autoincrement" description.
     */
    public static final String ID_COLUMN = "_id";
    public static final String TYPE_COLUMN = "type";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.tableName = name;
    }
}

package com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vmarinov on 11/20/2014.
 */
public abstract class BaseSQLiteOpenHelper
        extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "main.db";
    public static final String ID_COLUMN = "_id";
    public static final String TYPE_COLUMN = "type";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";

    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}

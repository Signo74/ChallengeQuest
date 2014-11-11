package com.swinginpenguin.vmarinov.challengequest.db.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vmarinov on 11/7/2014.
 */
public class ChapterDBHelper
        extends SQLiteOpenHelper{
    public static final String TABLE_NAME = "chapters";
    public static final String DATABASE_NAME = "main.db";
    private static int DATABASE_VERSION = 1;
    public static final String ID_COLUMN = "_id";
    public static final String TYPE_COLUMN = "type";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String EXP_REWARD = "experienceGranted";
    public static final String RANK = "rank";
    public static final String MAX_RANK = "maxrank";
    public static final String RECORD = "record";
    public static final String COMPLETION = "percentagecompleted";


    //TODO: Modify initial creation string to insert all necessary fields.
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "("
            + ID_COLUMN + " integer primary key autoincrement, "
            + TITLE_COLUMN + " text not null, "
            + TYPE_COLUMN + " integer, "
            + DESCRIPTION_COLUMN + " text, "
            + EXP_REWARD + " integer, "
            + RANK + " integer, "
            + MAX_RANK + " integer, "
            + RECORD + " real, "
            + COMPLETION + " integer, "
            + ");";

    public ChapterDBHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    private void createTable(SQLiteDatabase sqLiteDatabase) {
        //TODO: initialize the DB if it is missing.
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDB, int newDB) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }
}

package com.swinginpenguin.vmarinov.challengequest.db.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vmarinov on 11/10/2014.
 */
public class CreatureDBHelper
        extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "creatures";
    public static final String DATABASE_NAME = "main.db";
    private static int DATABASE_VERSION = 1;
    public static final String ID_COLUMN = "_id";
    public static final String TYPE_COLUMN = "type";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String EXPERIENCE = "experience";
    public static final String LEVEL = "level";
    public static final String GENDER = "gender";
    public static final String CREATURE_CLASS = "creatureclass";
    public static final String ATTRIBUTES = "attributes";
    public static final String STATS = "stats";
    public static final String SPECIAL_ABILITIES = "specialabilities";
    public static final String EQUIPPED_ITEMS = "equippeditems";
    public static final String AVAILABLE_LOOT = "availableloot";

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "("
            + ID_COLUMN + " integer primary key autoincrement, "
            + TITLE_COLUMN + " text not null, "
            + TYPE_COLUMN + " integer, "
            + DESCRIPTION_COLUMN + " text, "
            + EXPERIENCE + " integer, "
            + LEVEL + " integer, "
            + GENDER + " integer, "
            + CREATURE_CLASS + " integer, "
            + ATTRIBUTES + " text, "
            + STATS + " text, "
            + SPECIAL_ABILITIES + " text, "
            + EQUIPPED_ITEMS + " text, "
            + AVAILABLE_LOOT + " text, "
            + ");";

    public CreatureDBHelper(Context context) {
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

package com.swinginpenguin.vmarinov.challengequest.db.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Chapter;

/**
 * Created by vmarinov on 09-Dec-14.
 */
public class CampaignDBHelper
            extends BaseSQLiteOpenHelper {
    //TODO use rowID of SQLite and refcator all tables!
    public static final String TABLE_NAME = "campaigns";
    public static final String EXP_REWARD = "experienceGranted";
    public static final String RANK = "rank";
    public static final String MAX_RANK = "maxrank";
    public static final String RECORD = "record";
    public static final String COMPLETION = "percentagecompleted";
    public static final String QUESTS = "quests";

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
            + QUESTS + " text"
            + ");";

    public CampaignDBHelper(Context context) {
        super(context, TABLE_NAME, null);
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

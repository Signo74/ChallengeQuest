package com.swinginpenguin.vmarinov.challengequest.db.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetLastIdCallable;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by vmarinov on 11/10/2014.
 */
public class DbHelper
        extends SQLiteOpenHelper {
    // Common properties
    private static int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "main.db";
    public static final String ID_COLUMN = "_id";
    public static final String TYPE_COLUMN = "type";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";

    // Creatures table headers
    public static final String TABLE_NAME_CREATURES = "creatures";
    public static final String EXPERIENCE = "experience";
    public static final String LEVEL = "level";
    public static final String GENDER = "gender";
    public static final String RACE = "race";
    public static final String CREATURE_CLASS = "creatureclass";
    public static final String SUB_CLASS = "sublcass";
    public static final String ATTRIBUTES = "attributes";
    public static final String STATS = "stats";
    public static final String SPECIAL_ABILITIES = "specialabilities";
    public static final String EQUIPPED_ITEMS = "equippeditems";
    public static final String AVAILABLE_LOOT = "availableloot";
    public static final String CURRENT_CAMPAIGNS = "campaigns";
    public static final String CURRENT_QUESTS = "quests";
    public static final String COMPLETED_CAMPAIGNS = "completedcampaigns";
    public static final String COMPLETED_QUESTS = "completedquests";

    // Activity table includes campaigns, quests and chapters with the missing colums left empty
    public static final String TABLE_NAME_ACTIVITY = "activity";
    public static final String EXP_REWARD = "experienceGranted";
    public static final String RANK = "rank";
    public static final String MAX_RANK = "maxrank";
    public static final String RECORD = "record";
    public static final String COMPLETION = "percentagecompleted";
    public static final String QUESTS = "quests";
    public static final String CHAPTERS = "chapters";
    public int lastAvailableCreatureId = 0;
    public int lastAvailableActivityId = 0;

    private static final String DATABASE_CREATE_CREATURES = "create table " + TABLE_NAME_CREATURES + "("
            + ID_COLUMN + " integer primary key autoincrement, "
            + TYPE_COLUMN + " integer not null, "
            + TITLE_COLUMN + " text not null, "
            + DESCRIPTION_COLUMN + " text not null, "
            + EXPERIENCE + " integer, "
            + LEVEL + " integer, "
            + GENDER + " integer, "
            + RACE + " integer, "
            + CREATURE_CLASS + " integer, "
            + SUB_CLASS + " integer, "
            + ATTRIBUTES + " text, "
            + STATS + " text, "
            + SPECIAL_ABILITIES + " text, "
            + EQUIPPED_ITEMS + " text, "
            + AVAILABLE_LOOT + " text,"
            + CURRENT_CAMPAIGNS + " text, "
            + CURRENT_QUESTS + " text, "
            + COMPLETED_CAMPAIGNS + " text, "
            + COMPLETED_QUESTS + " text"
            + ");";

    private static final String DATABASE_CREATE_ACTIVITY = "create table " + TABLE_NAME_ACTIVITY + "("
            + ID_COLUMN + " integer primary key autoincrement, "
            + TYPE_COLUMN + " integer not null, "
            + TITLE_COLUMN + " text not null, "
            + DESCRIPTION_COLUMN + " text not null, "
            + EXP_REWARD + " integer, "
            + RANK + " integer, "
            + MAX_RANK + " integer, "
            + RECORD + " real, "
            + COMPLETION + " integer, "
            + QUESTS + " text, "
            + CHAPTERS + " text"
            + ");";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        GetLastIdCallable task = new GetLastIdCallable(this, TABLE_NAME_CREATURES);
        Future<Integer> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit (task);
        try {
            if (result.get() != null && result.get() > ErrorCodes.ERROR_OK.getErrorCode()) {
                lastAvailableCreatureId = result.get();
            } else {
                lastAvailableCreatureId = 0;
            }
        } catch (InterruptedException | ExecutionException ex) {
            Log.e("CampaignDBHelper.onCreate", "Error: " + ex + " was thrown while initializing Table: " + TABLE_NAME_CREATURES);
        }
        task = new GetLastIdCallable(this, TABLE_NAME_ACTIVITY);
        result = ExecutorServiceProvider.getInstance().getDbExecutor().submit (task);
        try {
            if (result.get() != null && result.get() > ErrorCodes.ERROR_OK.getErrorCode()) {
                lastAvailableActivityId = result.get();
            } else {
                lastAvailableActivityId = 0;
            }
        } catch (InterruptedException | ExecutionException ex) {
            Log.e("CampaignDBHelper.onCreate", "Error: " + ex + " was thrown while initializing Table: " + TABLE_NAME_CREATURES);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_CREATURES);
        sqLiteDatabase.execSQL(DATABASE_CREATE_ACTIVITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDB, int newDB) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CREATURES + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACTIVITY + ";");
        onCreate(sqLiteDatabase);
    }

    public int getLastAvailableCreatureId() {
        return lastAvailableCreatureId++;
    }

    public int getLastAvailableActivityId() {
        return lastAvailableActivityId;
    }
}

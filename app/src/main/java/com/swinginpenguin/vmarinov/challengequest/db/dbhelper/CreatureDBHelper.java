package com.swinginpenguin.vmarinov.challengequest.db.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetLastIdCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by vmarinov on 11/10/2014.
 */
public class CreatureDBHelper
        extends BaseSQLiteOpenHelper {
    public static final String TABLE_NAME = "creatures";
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

    public int lastAvailableId = 0;

    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "("
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

    public CreatureDBHelper(Context context) {
        super(context, TABLE_NAME, null);

        GetLastIdCallable task = new GetLastIdCallable(this);
        Future<Integer> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit (task);
        try {
            if (result.get() != null && result.get() > ErrorCodes.ERROR_OK.getErrorCode()) {
                lastAvailableId = result.get();
            } else {
                lastAvailableId = 0;
            }
        } catch (InterruptedException | ExecutionException ex) {
            Log.e("CampaignDBHelper.onCreate", "Error: " + ex + " was thrown while initializing Table: " + TABLE_NAME);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDB, int newDB) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }

    public int getLastAvailableId() {
        return lastAvailableId++;
    }
}

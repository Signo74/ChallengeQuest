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
 * Created by vmarinov on 11/7/2014.
 */
public class ChapterDBHelper
        extends BaseSQLiteOpenHelper {
    //TODO use rowID of SQLite and refcator all tables!
    public static final String TABLE_NAME = "chapters";
    public static final String EXP_REWARD = "experienceGranted";
    public static final String RANK = "rank";
    public static final String MAX_RANK = "maxrank";
    public static final String RECORD = "record";
    public static final String COMPLETION = "percentagecompleted";

    public int lastAvailableId = 0;


    //TODO: Modify initial creation string to insert all necessary fields.
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "("
            + ID_COLUMN + " integer primary key autoincrement, "
            + TYPE_COLUMN + " integer not null, "
            + TITLE_COLUMN + " text not null, "
            + DESCRIPTION_COLUMN + " text not null, "
            + EXP_REWARD + " integer, "
            + RANK + " integer, "
            + MAX_RANK + " integer, "
            + RECORD + " real, "
            + COMPLETION + " integer"
            + ");";

    public ChapterDBHelper(Context context) {
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

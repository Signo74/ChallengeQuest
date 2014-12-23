package com.swinginpenguin.vmarinov.challengequest.db.dao.callable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;

import java.util.concurrent.Callable;

/**
 * Created by vmarinov on 11-Dec-14.
 */
public class GetLastIdCallable
        implements Callable<Integer>{
    private final String ORDER = "_id DESC";
    private final String LIMIT = "1";
    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    // @ORDER - a WHERE SQL clause which defines which rows are to be selected.
    public GetLastIdCallable(BaseSQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
        database = dbHelper.getReadableDatabase();
    }

    @Override
    public Integer call() throws Exception {
        Integer result;
        Cursor queryResult;
        try {
            queryResult = database.query(dbHelper.tableName, null, null, null, null, null, ORDER, LIMIT);
            queryResult.moveToLast();
            if (queryResult.getInt(0) > 0) {
                return queryResult.getInt(0);
            } else {
                return 0;
            }
        } catch (SQLiteException ex) {
            Log.e(" GetLastIdCallable.call", "Error " + ex + " was thrown while processing all creatures.");
            return null;
        }
    }
}

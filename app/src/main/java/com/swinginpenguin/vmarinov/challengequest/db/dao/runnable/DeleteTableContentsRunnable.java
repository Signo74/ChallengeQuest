package com.swinginpenguin.vmarinov.challengequest.db.dao.runnable;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.DbHelper;

/**
 * Created by vmarinov on 11/25/2014.
 */
public class DeleteTableContentsRunnable implements Runnable {
    private SQLiteDatabase database;
    private String tableName;

    public DeleteTableContentsRunnable(DbHelper dbHelper, String tableName) {
        this.tableName = tableName;
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void run() {
        database.beginTransaction();
        try {
            database.delete(tableName, null, null);
            database.setTransactionSuccessful();
        } catch(SQLiteException ex) {
            Log.e("DeleteRunnable.run()", "Exception: " + ex + " was thrown while deleting table:" +
                    tableName);
        } finally {
            database.endTransaction();
        }
    }
}

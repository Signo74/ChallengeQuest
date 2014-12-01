package com.swinginpenguin.vmarinov.challengequest.db.dao.runnable;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;

/**
 * Created by vmarinov on 11/25/2014.
 */
public class DeleteRunnable implements Runnable {
    private String selection;
    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public DeleteRunnable(String selection, BaseSQLiteOpenHelper dbHelper){
        this.selection = selection;
        this.dbHelper = dbHelper;
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void run() {
        database.beginTransaction();
        try {
            database.delete(dbHelper.tableName, selection, null);
            database.setTransactionSuccessful();
        } catch(SQLiteException ex) {
            Log.e("DeleteRunnable.run()","Exception: " + ex + " was thrown while trying to delete" +
                    "entry with id: " + selection);
        } finally {
            database.endTransaction();
        }
    }
}

package com.swinginpenguin.vmarinov.challengequest.db.dao.runnable;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;

/**
 * Created by vmarinov on 11/25/2014.
 */
public class DeleteRunnable implements Runnable {
    private long entryId;
    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public DeleteRunnable(long id, BaseSQLiteOpenHelper dbHelper){
        this.entryId = id;
        this.dbHelper = dbHelper;
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void run() {
        database.beginTransaction();
        try {
            database.delete(dbHelper.TABLE_NAME, dbHelper.ID_COLUMN + "=" + entryId, null);
            database.setTransactionSuccessful();
        } catch(SQLiteException ex) {
            Log.e("DeleteRunnable.run()","Exception: " + ex + " was thrown while trying to delete" +
                    "entry with id: " + entryId);
        } finally {
            database.endTransaction();
        }
    }
}

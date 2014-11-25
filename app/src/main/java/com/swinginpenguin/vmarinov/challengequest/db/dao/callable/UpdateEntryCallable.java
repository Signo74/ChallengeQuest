package com.swinginpenguin.vmarinov.challengequest.db.dao.callable;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;

import java.util.concurrent.Callable;

/**
 * Created by vmarinov on 11/24/2014.
 */
public class UpdateEntryCallable implements Callable<Long> {

    private ContentValues values;
    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public UpdateEntryCallable(ContentValues values, BaseSQLiteOpenHelper dbHelper) {
        this.values = values;
        this.dbHelper = dbHelper;
        database = dbHelper.getWritableDatabase();
    }


    public Long call()
        throws SQLiteException {
        Long updateCount = ErrorCodes.DB_ERROR.getErrorCode();
        try {
            database.beginTransaction();
            //TODO get id from values!!!
            updateCount = new Long(database.update(dbHelper.TABLE_NAME), values, dbHelper.ID_COLUMN + " = " + 0, null));
            database.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e("InsertEntryCallable.call", "Error: " + ex + " was thrown while inserting in DB.");
            return ErrorCodes.DB_ERROR.getErrorCode();
        } finally {
            database.endTransaction();
        }
        return updateCount;
    }
}

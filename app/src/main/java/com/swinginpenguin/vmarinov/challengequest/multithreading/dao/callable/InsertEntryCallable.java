package com.swinginpenguin.vmarinov.challengequest.multithreading.dao.callable;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by vmarinov on 11/20/2014.
 */
public class InsertEntryCallable implements Callable<List<Cursor>> {

    private List<ContentValues> valuesList;
    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public InsertEntryCallable(List<ContentValues> values, BaseSQLiteOpenHelper dbHelper) {
        this.valuesList = values;
        this.dbHelper = dbHelper;
        database = dbHelper.getWritableDatabase();
    }

    public List<Cursor> call()
            throws SQLiteException {
        List<Cursor> returnCursors = new ArrayList<Cursor>();
        for (ContentValues values : valuesList) {
            try {
                database.beginTransaction();
                long insertID = database.insert(dbHelper.getDatabaseName(), null, values);
                Cursor cursor = database.query(dbHelper.getDatabaseName(), null, dbHelper.ID_COLUMN +
                        " = " + insertID, null, null, null, null);
                returnCursors.add(cursor);
                cursor.moveToFirst();
                cursor.close();
                database.setTransactionSuccessful();
            } catch (Exception ex) {
                Log.e("InsertEntryCallable.call", "Error: " + ex + " was thrown while inserting in DB.");
                return new ArrayList<Cursor>();
            } finally {
                database.endTransaction();
            }
        }
        return returnCursors;
    }
}

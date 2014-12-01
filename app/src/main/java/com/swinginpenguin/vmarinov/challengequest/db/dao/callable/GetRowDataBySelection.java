package com.swinginpenguin.vmarinov.challengequest.db.dao.callable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;

import java.util.concurrent.Callable;

/**
 * Created by vmarinov on 11/25/2014.
 */
public class GetRowDataBySelection implements Callable<Cursor> {

    private String selection;
    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    // @selection - a WHERE SQL clause which defines which rows are to be selected.
    public GetRowDataBySelection(String selection, BaseSQLiteOpenHelper dbHelper){
        this.selection = selection;
        this.dbHelper = dbHelper;
        database = dbHelper.getReadableDatabase();
    }

    @Override
    public Cursor call() throws Exception {
        Cursor result = null;
        try {
            result = database.query(dbHelper.tableName, null, selection, null, null, null, null);
        } catch (SQLiteException ex) {
            Log.e("CreaturesDAO.getAll", "Error " + ex + " was thrown while processing all creatures.");
            return null;
        }
        //Cursor will be closed later on
        return result;
    }
}

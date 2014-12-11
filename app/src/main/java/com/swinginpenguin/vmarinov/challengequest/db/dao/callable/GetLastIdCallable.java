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
        implements Callable<Long>{
    private String order = "column DESC";
    private String limit = "1";
    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    // @order - a WHERE SQL clause which defines which rows are to be selected.
    public GetLastIdCallable(BaseSQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
        database = dbHelper.getReadableDatabase();
    }

    @Override
    public Long call() throws Exception {
        Long result;
        Cursor queryResult = null;
        try {
            queryResult = database.query(dbHelper.tableName, null, null, null, null, null, order ,limit);
            result = queryResult.getLong(0);
        } catch (SQLiteException ex) {
            Log.e("CreaturesDAO.getAll", "Error " + ex + " was thrown while processing all creatures.");
            return null;
        }
        //Cursor will be closed later on
        return result;
    }
}

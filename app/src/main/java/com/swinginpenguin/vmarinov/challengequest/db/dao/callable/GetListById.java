package com.swinginpenguin.vmarinov.challengequest.db.dao.callable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by vmarinov on 11/25/2014.
 */
public class GetListById implements Callable<List<Cursor>> {

    // This is generic so that we can get access to different tables/db.
    private BaseSQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public GetListById(){
    }

    @Override
    public List<Cursor> call() throws Exception {
        List<Cursor> result = new ArrayList<();
        Cursor cursor = database.query(dbHelper.TABLE_NAME, null, null, null, null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result.add(creature);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("CreaturesDAO.getAll", "Error " + ex + " was thrown while processing all creatures.");
            return new ArrayList();
        } finally {
            cursor.close();
        }
    }
}

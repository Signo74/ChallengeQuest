package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.ChapterDBHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Chapter;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by vmarinov on 11/7/2014.
 */
public class ChaptersDAO {
    private SQLiteDatabase database;
    private ChapterDBHelper dbHelper;

    public ChaptersDAO(Context cntx) {
        dbHelper = new ChapterDBHelper(cntx);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Chapter insert(int type, String title, String description,
                          int expReward, int rank, int maxRank, long record, int completion) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(dbHelper.TYPE_COLUMN, type);
        values.put(dbHelper.TITLE_COLUMN, title);
        values.put(dbHelper.DESCRIPTION_COLUMN, description);
        values.put(dbHelper.EXP_REWARD, expReward);
        values.put(dbHelper.RANK, rank);
        values.put(dbHelper.MAX_RANK, maxRank);
        values.put(dbHelper.RECORD, record);
        values.put(dbHelper.COMPLETION, completion);

        try {
            database.beginTransaction();
            long insertID = database.insert(dbHelper.TABLE_NAME, null, values);
            Cursor cursor = database.query(dbHelper.TABLE_NAME, null, dbHelper.ID_COLUMN +
                    " = " + insertID, null, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            database.setTransactionSuccessful();

            EntryIdentity identity = new EntryIdentity(insertID, type, title, description);
            Chapter newChapter = new Chapter(identity, expReward, rank, maxRank, record, completion);

            return newChapter;
        } catch (Exception ex) {
            Log.e("ChaptersDAO.insert", "Error: " + ex + " was thrown while inserting chapter in DB.");
            EntryIdentity errorEntry = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(), "", "");
            Chapter errorChpater = new Chapter(errorEntry);
            return errorChpater;
        } finally {
            database.endTransaction();
        }
    }

    public boolean updateById(Chapter chapter) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        int updateCount = 0;
        values.put(dbHelper.TYPE_COLUMN, chapter.getIdentity().getType());
        values.put(dbHelper.TITLE_COLUMN, chapter.getIdentity().getTitle());
        values.put(dbHelper.DESCRIPTION_COLUMN, chapter.getIdentity().getDescription());
        values.put(dbHelper.EXP_REWARD, chapter.getExperienceReward());
        values.put(dbHelper.RANK, chapter.getRank());
        values.put(dbHelper.MAX_RANK, chapter.getRank());
        values.put(dbHelper.RECORD, chapter.getRecord());
        values.put(dbHelper.COMPLETION, chapter.getPercentageCompleted());
        Log.d("ChaptersDAO.updateById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + chapter.getIdentity().getId());
        database.beginTransaction();
        try {
            Log.d("ChaptersDAO.updateById", "Updating chapter entry with Id " + chapter.getIdentity().getId());
            updateCount = database.update(dbHelper.TABLE_NAME, values, dbHelper.ID_COLUMN + " = " + chapter.getIdentity().getId(), null);
            database.setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e("ChaptersDAO.updateById", "Error: " + ex + " was thrown while updating chapter in DB.");
            return false;
        } finally {
            database.endTransaction();
        }

        return updateCount > 0;
    }

    public int updateListById(List<Chapter> chapters) {
        //TODO replace .toString with DBUtils method
        ListIterator<Chapter> iterator = chapters.listIterator();
        int updateCount = 0;
        database.beginTransaction();
        try {
            while (iterator.hasNext()) {
                Chapter chapter = iterator.next();
                ContentValues values = new ContentValues();
                values.put(dbHelper.TYPE_COLUMN, chapter.getIdentity().getType());
                values.put(dbHelper.TITLE_COLUMN, chapter.getIdentity().getTitle());
                values.put(dbHelper.DESCRIPTION_COLUMN, chapter.getIdentity().getDescription());
                values.put(dbHelper.EXP_REWARD, chapter.getExperienceReward());
                values.put(dbHelper.RANK, chapter.getRank());
                values.put(dbHelper.MAX_RANK, chapter.getMaxRank());
                values.put(dbHelper.RECORD, chapter.getRecord());
                values.put(dbHelper.COMPLETION, chapter.getPercentageCompleted());
                Log.d("ChaptersDAO.updateListById", "Updating chapter entry with id " +
                        chapter.getIdentity().getId() + " with values {}" + values.valueSet());
                updateCount += database.update(dbHelper.TABLE_NAME, values, dbHelper.ID_COLUMN +
                        " = " + chapter.getIdentity().getId(), null);
                database.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.e("ChaptersDAO.updateListById", "Error: " + ex +
                    " was thrown while updating list of chapters in DB.");
            return -1;
        } finally {
            database.endTransaction();
        }

        return updateCount;
    }

    public List<Chapter> getAll() {
        List<Chapter> allChapters = new ArrayList<Chapter>();
        Cursor cursor = database.query(dbHelper.TABLE_NAME, null, null, null, null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Chapter chapter = cursorToObject(cursor);
                allChapters.add(chapter);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("ChaptersDAO.getAll", "Error " + ex +" was thrown while processing all chapters.");
            return new ArrayList();
        } finally {
            cursor.close();
        }
        return allChapters;
    }

    public void delete(Chapter chapter) {
        long id = chapter.getIdentity().getId();
        database.beginTransaction();
        try {
            database.delete(dbHelper.TABLE_NAME, dbHelper.ID_COLUMN + "=" + id, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void deleteList(List<Chapter> chapters) {
        ListIterator<Chapter> iterator = chapters.listIterator();
        while (iterator.hasNext()) {
            delete(iterator.next());
        }
    }

    public void deleteAll(){
        database.beginTransaction();
        try {
            database.delete(dbHelper.TABLE_NAME, null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    private Chapter cursorToObject(Cursor cursor) {
        try {
            long id = cursor.getPosition();
            Integer type = cursor.getInt(1);
            String title = cursor.getString(2);
            String description = cursor.getString(3);

            EntryIdentity identity = new EntryIdentity(id, type, title, description);
            int expReward = cursor.getInt(4);
            int rank = cursor.getInt(5);
            int maxRank = cursor.getInt(6);
            long record = cursor.getLong(7);
            int completion = cursor.getInt(8);

            Chapter chapter = new Chapter(identity, expReward, rank, maxRank, record, completion);

            Log.d("ChaptersDAO.cursorToObject","Creating new chapter with id " +
                    chapter.getIdentity().getId() + ". Chapter: " + chapter);
            return chapter;
        } catch (Exception ex) {
            Log.e("ChaptersDAO.cursorToObject", "Exception " + ex +" thrown while creating new chapter");
            EntryIdentity errorIdentity = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(),"", "");
            Chapter errorChapter = new Chapter(errorIdentity);
            return errorChapter;
        }
    }
}

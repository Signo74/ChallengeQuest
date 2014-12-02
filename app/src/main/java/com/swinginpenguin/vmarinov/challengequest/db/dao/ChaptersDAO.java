package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetRowDataBySelection;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.InsertEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.UpdateEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteTableContentsRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.ChapterDBHelper;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Chapter;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by vmarinov on 11/7/2014.
 */
public class ChaptersDAO {
    private ChapterDBHelper dbHelper;

    public ChaptersDAO(Context cntx) {
        dbHelper = new ChapterDBHelper(cntx);

    }

    public Boolean insert(Chapter chapter) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(BaseSQLiteOpenHelper.TYPE_COLUMN, chapter.getIdentity().getType());
        values.put(BaseSQLiteOpenHelper.TITLE_COLUMN, chapter.getIdentity().getTitle());
        values.put(BaseSQLiteOpenHelper.DESCRIPTION_COLUMN, chapter.getIdentity().getDescription());
        values.put(ChapterDBHelper.EXP_REWARD, chapter.getExperienceReward());
        values.put(ChapterDBHelper.RANK, chapter.getRank());
        values.put(ChapterDBHelper.MAX_RANK, chapter.getMaxRank());
        values.put(ChapterDBHelper.RECORD, chapter.getRecord());
        values.put(ChapterDBHelper.COMPLETION, chapter.getPercentageCompleted());

        try {
            Log.d("CreaturesDAO.insert", "Inserting Creature with id: " + chapter.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("ChaptersDAO.insert", "Error: " + ex + " was thrown while inserting chapter in DB.");
            return false;
        }
    }

    public boolean updateById(Chapter chapter) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(BaseSQLiteOpenHelper.TYPE_COLUMN, chapter.getIdentity().getType());
        values.put(BaseSQLiteOpenHelper.TITLE_COLUMN, chapter.getIdentity().getTitle());
        values.put(BaseSQLiteOpenHelper.DESCRIPTION_COLUMN, chapter.getIdentity().getDescription());
        values.put(ChapterDBHelper.EXP_REWARD, chapter.getExperienceReward());
        values.put(ChapterDBHelper.RANK, chapter.getRank());
        values.put(ChapterDBHelper.MAX_RANK, chapter.getRank());
        values.put(ChapterDBHelper.RECORD, chapter.getRecord());
        values.put(ChapterDBHelper.COMPLETION, chapter.getPercentageCompleted());
        Log.d("ChaptersDAO.updateById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + chapter.getIdentity().getId());
        try {
            Log.d("ChaptersDAO.updateById", "Updating chapter entry with Id " + chapter.getIdentity().getId());
            UpdateEntryCallable task = new UpdateEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("ChaptersDAO.updateById", "Error: " + ex + " was thrown while updating chapter in DB.");
            return false;
        }
    }

    public long updateListById(List<Chapter> chapters) {
        ListIterator<Chapter> iterator = chapters.listIterator();
        long updateCount = 0;
        while (iterator.hasNext()) {
            Chapter chapter = iterator.next();
            if (updateById(chapter)) {
                updateCount++;
            } else {
                Log.e("ChaptersDAO.updateListById", "An error was thrown while updating list of " +
                        "chapters in DB with chapterId: " + chapter.getIdentity().getId());
                return ErrorCodes.DB_ERROR.getErrorCode();
            }
        }
        return updateCount;
    }

    public List<Chapter> getAll()
            throws ExecutionException, InterruptedException {
        List<Chapter> allChapters = new ArrayList<Chapter>();
        GetRowDataBySelection task = new GetRowDataBySelection(null, dbHelper);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Chapter chapter = cursorToObject(cursor);
                allChapters.add(chapter);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("ChaptersDAO.getAll", "Error " + ex +" was thrown while processing all creatures.");
            return new ArrayList();
        } finally {
            result.get().close();
            cursor.close();
        }
        return allChapters;
    }
    public List<Chapter> getList(String selection)
            throws ExecutionException, InterruptedException {
        List<Chapter> allChapters = new ArrayList<Chapter>();
        GetRowDataBySelection task = new GetRowDataBySelection(null, dbHelper);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Chapter chapter = cursorToObject(cursor);
                allChapters.add(chapter);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("ChaptersDAO.getList", "Error " + ex +" was thrown while processing all creatures.");
            return new ArrayList();
        } finally {
            result.get().close();
            cursor.close();
        }
        return allChapters;
    }

    public void delete(Chapter chapter) {
        long id = chapter.getIdentity().getId();
        String selection = BaseSQLiteOpenHelper.ID_COLUMN + "=" + id;
        Log.d("CreaturesDAO.delete","About to delete DB entry: " + id + " in table: " + dbHelper.tableName);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteList(List<Chapter> chapters) {
        String selection = dbHelper.ID_COLUMN + " IN ";
        ListIterator<Chapter> iterator = chapters.listIterator();
        while (iterator.hasNext()) {
            long id = iterator.next().getIdentity().getId();
            selection.concat(id + "','");
        }
        selection.concat(")");
        Log.d("CreaturesDAO.","About to delete DB entries with: " + selection + " in table: " + dbHelper.tableName);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteAll(){
        DeleteTableContentsRunnable task = new DeleteTableContentsRunnable(dbHelper);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
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

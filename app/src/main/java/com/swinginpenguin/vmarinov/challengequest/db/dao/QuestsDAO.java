package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetRowDataBySelection;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.InsertEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteTableContentsRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.QuestsDBHelper;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Chapter;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

/**
 * Created by victorm on 10/23/2014..
 */
public class QuestsDAO {
    private QuestsDBHelper dbHelper;

    public QuestsDAO(Context cntx) {
        dbHelper = new QuestsDBHelper(cntx);
    }

    public Boolean insert(Quest quest) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(BaseSQLiteOpenHelper.TYPE_COLUMN, quest.getIdentity().getType());
        values.put(BaseSQLiteOpenHelper.TITLE_COLUMN, quest.getIdentity().getTitle());
        values.put(BaseSQLiteOpenHelper.DESCRIPTION_COLUMN, quest.getIdentity().getDescription());

        String chaptersString = quest.getChapters().toString();

        values.put(QuestsDBHelper.CHAPTERS, chaptersString);
        values.put(QuestsDBHelper.EXP_REWARD, quest.getExperienceReward());
        values.put(QuestsDBHelper.RANK, quest.getRank());
        values.put(QuestsDBHelper.MAX_RANK, quest.getMaxRank());
        values.put(QuestsDBHelper.COMPLETION, quest.getPercentageCompleted());

        try {
            Log.d("QuestsDAO.insert", "Inserting Quest with id: " + quest.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().dbExecutor.submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("QuestsDAO.insert", "Error: " + ex + " was thrown while inserting quest in DB.");
            return false;
        }
    }

    public boolean updateById(Quest quest) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(BaseSQLiteOpenHelper.TYPE_COLUMN, quest.getIdentity().getType());
        values.put(BaseSQLiteOpenHelper.TITLE_COLUMN, quest.getIdentity().getTitle());
        values.put(BaseSQLiteOpenHelper.DESCRIPTION_COLUMN, quest.getIdentity().getDescription());
        values.put(QuestsDBHelper.CHAPTERS, quest.getChapters().toString());
        values.put(QuestsDBHelper.EXP_REWARD, quest.getExperienceReward());
        values.put(QuestsDBHelper.RANK, quest.getRank());
        values.put(QuestsDBHelper.MAX_RANK, quest.getRank());
        values.put(QuestsDBHelper.COMPLETION, quest.getPercentageCompleted());
        Log.d("QuestsDAO.updateById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + quest.getIdentity().getId());
        try {
            Log.d("QuestsDAO.updateById", "Updating quest entry with Id "+ quest.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().dbExecutor.submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("QuestsDAO.updateById", "Error: " + ex + " was thrown while updating quest in DB.");
            return false;
        }
    }

    public long updateListById(List<Quest> quests) {
        ListIterator<Quest> iterator = quests.listIterator();
        int updateCount = 0;
        while (iterator.hasNext()) {
            Quest quest = iterator.next();
            if (updateById(quest)) {
                updateCount++;
            } else {
                Log.e("QuestsDAO.updateListById", "An error was thrown while updating list of " +
                        "quests in DB with creatureId: " + quest.getIdentity().getId());
                return ErrorCodes.DB_ERROR.getErrorCode();
            }
        }
        return updateCount;
    }

    public List<Quest> getAll()
            throws ExecutionException, InterruptedException {
        List<Quest> allQuests = new ArrayList<Quest>();
        GetRowDataBySelection task = new GetRowDataBySelection(null, dbHelper);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().dbExecutor.submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Quest quest = cursorToObject(cursor);
                allQuests.add(quest);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("QuestsDAO.getAll", "Error " + ex +" was thrown while processing all quests.");
            return new ArrayList();
        } finally {
            result.get().close();
            cursor.close();
        }
        return allQuests;
    }

    public void delete(Quest quest) {
        long id = quest.getIdentity().getId();
        String selection = BaseSQLiteOpenHelper.ID_COLUMN + "=" + id;
        Log.d("QuestsDAO.delete","About to delete DB entry: " + id + " in table: " + dbHelper.tableName);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper);
        ExecutorServiceProvider.getInstance().dbExecutor.submit(task);
    }

    public void deleteList(List<Quest> quests) {
        String selection = dbHelper.ID_COLUMN + " IN ";
        ListIterator<Quest> iterator = quests.listIterator();
        while (iterator.hasNext()) {
            long id = iterator.next().getIdentity().getId();
            selection.concat(id + "','");
        }
        selection.concat(")");
        Log.d("QuestsDAO.","About to delete DB entries with: " + selection + " in table: " + dbHelper.tableName);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper);
        ExecutorServiceProvider.getInstance().dbExecutor.submit(task);
    }

    public void deleteAll(){
        DeleteTableContentsRunnable task = new DeleteTableContentsRunnable(dbHelper);
        ExecutorServiceProvider.getInstance().dbExecutor.submit(task);
    }

    private Quest cursorToObject(Cursor cursor) {
        try {
            long id = cursor.getPosition();
            Integer type = cursor.getInt(1);
            String title = cursor.getString(2);
            String description = cursor.getString(3);

            EntryIdentity identity = new EntryIdentity(id, type, title, description);

            //TODO Create a list of Chapters from cursor.getString(4);
            List<Chapter> chapters = new ArrayList<Chapter>();

            int expReward = cursor.getInt(5);
            int rank = cursor.getInt(6);
            int maxRank = cursor.getInt(7);
            int completion = cursor.getInt(8);

            Quest newQuest = new Quest(identity, chapters, expReward, rank, maxRank, completion);

            Log.d("QuestsDAO.cursorToObject","Creating new quest with id " +
                   newQuest.getIdentity().getId() + ". Quest: " + newQuest);
            return newQuest;
        } catch (Exception ex) {
            Log.e("QuestsDAO.cursorToObject", "Exception " + ex +" thrown while creating new quest");
            EntryIdentity errorIdentity = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(),"", "");
            Quest errorQuest = new Quest(errorIdentity);
            return null;
        }
    }
}
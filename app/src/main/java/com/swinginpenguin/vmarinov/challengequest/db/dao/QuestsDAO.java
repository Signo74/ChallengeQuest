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


import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.QuestsDBHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Chapter;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;

/**
 * Created by victorm on 10/23/2014..
 */
public class QuestsDAO {
    private SQLiteDatabase database;
    private QuestsDBHelper dbHelper;

    public QuestsDAO(Context cntx) {
        dbHelper = new QuestsDBHelper(cntx);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Quest insertQuest(int type, String title, String description, List<Chapter> chapters,
                             int expReward, int rank, int maxRank, int completion) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.TYPE_COLUMN, type);
        values.put(dbHelper.TITLE_COLUMN, title);
        values.put(dbHelper.DESCRIPTION_COLUMN, description);

        String chaptersString = chapters.toString();

        values.put(dbHelper.CHAPTERS, chaptersString);
        values.put(dbHelper.EXP_REWARD, expReward);
        values.put(dbHelper.RANK, rank);
        values.put(dbHelper.MAX_RANK, maxRank);
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
            Quest newQuest = new Quest(identity, chapters, expReward, rank, maxRank, completion);

            return newQuest;
        } catch (Exception ex) {
            Log.e("QuestsDAO.insertQuest", "Error: " + ex + " was thrown while inserting quest in DB.");
            EntryIdentity errorEntry = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(), "", "");
            Quest errorQuest = new Quest(errorEntry);
            return errorQuest;
        } finally {
            database.endTransaction();
        }
    }

    public boolean updateQuestById(Quest quest) {
        ContentValues values = new ContentValues();
        int updateCount = 0;
        values.put(dbHelper.TYPE_COLUMN, quest.getIdentity().getType());
        values.put(dbHelper.TITLE_COLUMN, quest.getIdentity().getTitle());
        values.put(dbHelper.DESCRIPTION_COLUMN, quest.getIdentity().getDescription());
        values.put(dbHelper.CHAPTERS, quest.getChapters().toString());
        values.put(dbHelper.EXP_REWARD, quest.getExperienceReward());
        values.put(dbHelper.RANK, quest.getRank());
        values.put(dbHelper.MAX_RANK, quest.getRank());
        values.put(dbHelper.COMPLETION, quest.getPercentageCompleted());
        Log.d("QuestsDAO.updateQuestById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + quest.getIdentity().getId());
        database.beginTransaction();
        try {
            Log.d("QuestsDAO.updateQuestById", "Updating quest entry with Id "+ quest.getIdentity().getId());
            updateCount = database.update(dbHelper.TABLE_NAME, values, dbHelper.ID_COLUMN + " = " + quest.getIdentity().getId(), null);
            database.setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e("QuestsDAO.updateQuestById", "Error: " + ex + " was thrown while updating quest in DB.");
            return false;
        } finally {
            database.endTransaction();
        }

        return updateCount > 0;
    }

    public int updateListOfQuestsById(List<Quest> quests) {
        ListIterator<Quest> iterator = quests.listIterator();
        int updateCount = 0;
        database.beginTransaction();
        try {
            while (iterator.hasNext()) {
                Quest quest = iterator.next();
                ContentValues values = new ContentValues();
                values.put(dbHelper.TYPE_COLUMN, quest.getIdentity().getType());
                values.put(dbHelper.TITLE_COLUMN, quest.getIdentity().getTitle());
                values.put(dbHelper.DESCRIPTION_COLUMN, quest.getIdentity().getDescription());
                values.put(dbHelper.CHAPTERS, quest.getChapters().toString());
                values.put(dbHelper.EXP_REWARD, quest.getExperienceReward());
                values.put(dbHelper.RANK, quest.getRank());
                values.put(dbHelper.MAX_RANK, quest.getMaxRank());
                values.put(dbHelper.COMPLETION, quest.getPercentageCompleted());
                Log.d("QuestsDAO.updateListOfTasksById", "Updating quest entry with id " +
                        quest.getIdentity().getId() + " with values {}" + values.valueSet());
                updateCount += database.update(dbHelper.TABLE_NAME, values, dbHelper.ID_COLUMN +
                                                " = " + quest.getIdentity().getId(), null);
                database.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.e("QuestsDAO.updateListOfChaptersById", "Error: " + ex +
                    " was thrown while updating list of quests in DB.");
            return -1;
        } finally {
            database.endTransaction();
        }

        return updateCount;
    }

    public List<Quest> getAllQuests() {
        List<Quest> allQuests = new ArrayList<Quest>();
        Cursor cursor = database.query(dbHelper.TABLE_NAME, null, null, null, null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Quest quest = cursorToQuest(cursor);
                allQuests.add(quest);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("QuestsDAO.getAllChapters", "Error " + ex +" was thrown while processing all quests.");
            return new ArrayList();
        } finally {
            cursor.close();
        }
        return allQuests;
    }

    public void deleteQuest(Quest quest) {
        long id = quest.getIdentity().getId();
        database.beginTransaction();
        try {
            database.delete(dbHelper.TABLE_NAME, dbHelper.ID_COLUMN + "=" + id, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void deleteListOfQuests(List<Quest> quests) {
        ListIterator<Quest> iterator = quests.listIterator();
        while (iterator.hasNext()) {
            deleteQuest(iterator.next());
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

    private Quest cursorToQuest(Cursor cursor) {
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

            Log.d("QuestsDAO.cursorToQuest","Creating new quest with id " +
                   newQuest.getIdentity().getId() + ". Quest: " + newQuest);
            return newQuest;
        } catch (Exception ex) {
            Log.e("QuestsDAO.cursorToQuest", "Exception " + ex +" thrown while creating new quest");
            EntryIdentity errorIdentity = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(),"", "");
            Quest errorQuest = new Quest(errorIdentity);
            return null;
        }
    }
}
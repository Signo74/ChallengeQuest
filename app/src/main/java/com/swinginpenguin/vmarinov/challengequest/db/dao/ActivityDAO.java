package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetRowDataBySelection;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.InsertEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteTableContentsRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.DbHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Activity;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

/**
 * Created by victorm on 10/23/2014..
 */
public class ActivityDAO {
    private DbHelper dbHelper;
    private static ActivityDAO instance = null;

    public ActivityDAO(Context cntx) {
        dbHelper = new DbHelper(cntx);
    }


    public static ActivityDAO getInstance(Context cntx) {
        if (instance == null) {
            instance = new ActivityDAO(cntx);
        }
        return instance;
    }
    public Boolean insert(Activity activity) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(DbHelper.TYPE_COLUMN, activity.getIdentity().getType());
        values.put(DbHelper.TITLE_COLUMN, activity.getIdentity().getTitle());
        values.put(DbHelper.DESCRIPTION_COLUMN, activity.getIdentity().getDescription());
        String chaptersString;
        if (activity.getChapters() != null) {
            chaptersString = activity.getChapters().toString();
        } else {
            chaptersString = "";
        }

        values.put(DbHelper.CHAPTERS, chaptersString);
        values.put(DbHelper.EXP_REWARD, activity.getExperienceReward());
        values.put(DbHelper.RANK, activity.getRank());
        values.put(DbHelper.MAX_RANK, activity.getMaxRank());
        values.put(DbHelper.COMPLETION, activity.getPercentageCompleted());

        try {
            Log.d("ActivityDAO.insert", "Inserting Quest with id: " + activity.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper, DbHelper.TABLE_NAME_ACTIVITY);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("ActivityDAO.insert", "Error: " + ex + " was thrown while inserting quest in DB.");
            return false;
        }
    }

    public boolean updateById(Activity activity) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(DbHelper.TYPE_COLUMN, activity.getIdentity().getType());
        values.put(DbHelper.TITLE_COLUMN, activity.getIdentity().getTitle());
        values.put(DbHelper.DESCRIPTION_COLUMN, activity.getIdentity().getDescription());
        values.put(DbHelper.CHAPTERS, activity.getChapters().toString());
        values.put(DbHelper.EXP_REWARD, activity.getExperienceReward());
        values.put(DbHelper.RANK, activity.getRank());
        values.put(DbHelper.MAX_RANK, activity.getRank());
        values.put(DbHelper.COMPLETION, activity.getPercentageCompleted());
        Log.d("ActivityDAO.updateById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + activity.getIdentity().getId());
        try {
            Log.d("ActivityDAO.updateById", "Updating quest entry with Id "+ activity.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper, DbHelper.TABLE_NAME_ACTIVITY);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("ActivityDAO.updateById", "Error: " + ex + " was thrown while updating quest in DB.");
            return false;
        }
    }

    public long updateListById(List<Activity> activities) {
        ListIterator<Activity> iterator = activities.listIterator();
        int updateCount = 0;
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (updateById(activity)) {
                updateCount++;
            } else {
                Log.e("ActivityDAO.updateListById", "An error was thrown while updating list of " +
                        "quests in DB with creatureId: " + activity.getIdentity().getId());
                return ErrorCodes.DB_ERROR.getErrorCode();
            }
        }
        return updateCount;
    }

    public List<Activity> getAll(Integer activityType) throws ExecutionException, InterruptedException {
        List<Activity> allActivities = new ArrayList<Activity>();
        String selection = null;
        if (activityType != null) {
            selection = DbHelper.TYPE_COLUMN + "=" + activityType;
        }
        GetRowDataBySelection task = new GetRowDataBySelection(selection, dbHelper, DbHelper.TABLE_NAME_ACTIVITY);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Activity activity = cursorToObject(cursor);
                allActivities.add(activity);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("ActivityDAO.getAll", "Error " + ex +" was thrown while initializing DAO.");
            return new ArrayList();
        }finally {
            result.get().close();
            cursor.close();
        }
        return allActivities;
    }

    public void delete(Activity activity) {
        long id = activity.getIdentity().getId();
        String selection = DbHelper.ID_COLUMN + "=" + id;
        Log.d("ActivityDAO.delete","About to delete DB entry: " + id + " in table: " + DbHelper.TABLE_NAME_ACTIVITY);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper, DbHelper.TABLE_NAME_ACTIVITY);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteList(List<Activity> activities) {
        String selection = dbHelper.ID_COLUMN + " IN ";
        ListIterator<Activity> iterator = activities.listIterator();
        while (iterator.hasNext()) {
            long id = iterator.next().getIdentity().getId();
            selection.concat(id + "','");
        }
        selection.concat(")");
        Log.d("ActivityDAO.","About to delete DB entries with: " + selection + " in table: " + DbHelper.TABLE_NAME_ACTIVITY);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper, DbHelper.TABLE_NAME_ACTIVITY);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteAll(){
        DeleteTableContentsRunnable task = new DeleteTableContentsRunnable(dbHelper, DbHelper.TABLE_NAME_ACTIVITY);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    private Activity cursorToObject(Cursor cursor) {
        try {
            int id = cursor.getInt(0);
            Integer type = cursor.getInt(1);
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            EntryIdentity identity = new EntryIdentity(id, type, title, description);
            int expReward = cursor.getInt(4);
            int rank = cursor.getInt(5);
            int maxRank = cursor.getInt(6);
            int completion = cursor.getInt(7);
            //TODO Create a list of Chapters from cursor.getString(8);
            List<Activity> quests = new ArrayList<Activity>();
            //TODO Create a list of Chapters from cursor.getString(9);
            List<Activity> chapters = new ArrayList<Activity>();

            Activity newActivity = new Activity(identity, expReward, rank, maxRank, completion, quests, chapters);

            Log.d("ActivityDAO.cursorToObject","Creating new activity with id " +
                   newActivity.getIdentity().getId() + ". Activity: " + newActivity);
            return newActivity;
        } catch (Exception ex) {
            Log.e("ActivityDAO.cursorToObject", "Exception " + ex +" thrown while creating new quest");
            EntryIdentity errorIdentity = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(),"", "");
            Activity errorActivity = new Activity(errorIdentity);
            return null;
        }
    }

    public int getLastAvailableId() {
        return dbHelper.getLastAvailableActivityId();
    }
}
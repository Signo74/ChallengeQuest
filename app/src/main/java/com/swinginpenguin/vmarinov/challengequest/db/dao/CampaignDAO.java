package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetLastIdCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetRowDataBySelection;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.InsertEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.UpdateEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteTableContentsRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.CampaignDBHelper;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.base.BaseSQLiteOpenHelper;
import com.swinginpenguin.vmarinov.challengequest.db.utils.DBUtils;
import com.swinginpenguin.vmarinov.challengequest.model.Campaign;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by vmarinov on 09-Dec-14.
 */
public class CampaignDAO {
    private CampaignDBHelper dbHelper;
    private long lastAvailableId;

    public CampaignDAO(Context cntx) {
        dbHelper = new CampaignDBHelper(cntx);

        GetLastIdCallable task = new GetLastIdCallable(dbHelper);
        Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit (task);
        try {
            if (result.get() != null && result.get() > ErrorCodes.ERROR_OK.getErrorCode()) {
                lastAvailableId = result.get();
            } else {
                lastAvailableId = 0;
            }
        } catch (InterruptedException | ExecutionException ex) {
            Log.e("CampaignDAO constructor", "Error: " + ex + " was thrown while initializing DAO.");
        }
    }

    public Boolean insert(Campaign campaign) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(BaseSQLiteOpenHelper.TYPE_COLUMN, campaign.getIdentity().getType());
        values.put(BaseSQLiteOpenHelper.TITLE_COLUMN, campaign.getIdentity().getTitle());
        values.put(BaseSQLiteOpenHelper.DESCRIPTION_COLUMN, campaign.getIdentity().getDescription());
        values.put(CampaignDBHelper.EXP_REWARD, campaign.getExperienceReward());
        values.put(CampaignDBHelper.RANK, campaign.getRank());
        values.put(CampaignDBHelper.MAX_RANK, campaign.getMaxRank());
        values.put(CampaignDBHelper.RECORD, campaign.getRecord());
        values.put(CampaignDBHelper.COMPLETION, campaign.getPercentageCompleted());

        try {
            Log.d("CampaignDAO.insert", "Inserting Campaign with id: " + campaign.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("CampaignDAO.insert", "Error: " + ex + " was thrown while inserting campaign in DB.");
            return false;
        }
    }

    public boolean updateById(Campaign campaign) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(BaseSQLiteOpenHelper.TYPE_COLUMN, campaign.getIdentity().getType());
        values.put(BaseSQLiteOpenHelper.TITLE_COLUMN, campaign.getIdentity().getTitle());
        values.put(BaseSQLiteOpenHelper.DESCRIPTION_COLUMN, campaign.getIdentity().getDescription());
        values.put(CampaignDBHelper.EXP_REWARD, campaign.getExperienceReward());
        values.put(CampaignDBHelper.RANK, campaign.getRank());
        values.put(CampaignDBHelper.MAX_RANK, campaign.getRank());
        values.put(CampaignDBHelper.RECORD, campaign.getRecord());
        values.put(CampaignDBHelper.COMPLETION, campaign.getPercentageCompleted());
        Log.d("CampaignDAO.updateById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + campaign.getIdentity().getId());
        try {
            Log.d("CampaignDAO.updateById", "Updating campaign entry with Id " + campaign.getIdentity().getId());
            UpdateEntryCallable task = new UpdateEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("CampaignDAO.updateById", "Error: " + ex + " was thrown while updating campaign in DB.");
            return false;
        }
    }

    public long updateListById(List<Campaign> campaigns) {
        ListIterator<Campaign> iterator = campaigns.listIterator();
        long updateCount = 0;
        while (iterator.hasNext()) {
            Campaign campaign = iterator.next();
            if (updateById(campaign)) {
                updateCount++;
            } else {
                Log.e("CampaignDAO.updateListById", "An error was thrown while updating list of " +
                        "campaigns in DB with campaignId: " + campaign.getIdentity().getId());
                return ErrorCodes.DB_ERROR.getErrorCode();
            }
        }
        return updateCount;
    }

    public List<Campaign> getAll()
            throws ExecutionException, InterruptedException {
        List<Campaign> allCampaigns = new ArrayList<Campaign>();
        GetRowDataBySelection task = new GetRowDataBySelection(null, dbHelper);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Campaign campaign = cursorToObject(cursor);
                allCampaigns.add(campaign);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("CampaignDAO.getAll", "Error " + ex +" was thrown while processing all Campaigns.");
            return new ArrayList<Campaign>();
        } finally {
            result.get().close();
            cursor.close();
        }
        return allCampaigns;
    }
    public List<Campaign> getList(String selection)
            throws ExecutionException, InterruptedException {
        List<Campaign> allCampaigns = new ArrayList<Campaign>();
        GetRowDataBySelection task = new GetRowDataBySelection(null, dbHelper);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Campaign campaign = cursorToObject(cursor);
                allCampaigns.add(campaign);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("CampaignDAO.getList", "Error " + ex +" was thrown while processing all Campaigns.");
            return new ArrayList();
        } finally {
            result.get().close();
            cursor.close();
        }
        return allCampaigns;
    }

    public void delete(Campaign campaign) {
        long id = campaign.getIdentity().getId();
        String selection = BaseSQLiteOpenHelper.ID_COLUMN + "=" + id;
        Log.d("CampaignDAO.delete","About to delete DB entry: " + id + " in table: " + dbHelper.tableName);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteList(List<Campaign> campaigns) {
        String selection = dbHelper.ID_COLUMN + " IN ";
        ListIterator<Campaign> iterator = campaigns.listIterator();
        while (iterator.hasNext()) {
            long id = iterator.next().getIdentity().getId();
            selection.concat(id + "','");
        }
        selection.concat(")");
        Log.d("CampaignDAO.","About to delete DB entries with: " + selection + " in table: " + dbHelper.tableName);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteAll(){
        DeleteTableContentsRunnable task = new DeleteTableContentsRunnable(dbHelper);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    private Campaign cursorToObject(Cursor cursor) {
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
            // TODO use DBUtils to convert (9)
            List<Quest> quests = new ArrayList<Quest>();
            Campaign campaign = new Campaign(identity, expReward, rank, maxRank, record, completion, quests);

            Log.d("CampaignDAO.cursorToObject","Creating new campaign with id " +
                    campaign.getIdentity().getId() + ". campaign: " + campaign);
            return campaign;
        } catch (Exception ex) {
            Log.e("CampaignDAO.cursorToObject", "Exception " + ex +" thrown while creating new campaign");
            EntryIdentity errorIdentity = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(),"", "");
            Campaign errorCampaign = new Campaign(errorIdentity);
            return errorCampaign;
        }
    }

    public long getLastAvailableId() {
        return lastAvailableId++;
    }
}

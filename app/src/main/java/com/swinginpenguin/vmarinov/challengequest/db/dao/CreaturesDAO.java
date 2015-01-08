package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.GetRowDataBySelection;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.UpdateEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteTableContentsRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.DbHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Ability;
import com.swinginpenguin.vmarinov.challengequest.model.Attribute;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.db.utils.DBUtils;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.InsertEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by vmarinov on 11/11/2014.
 */
public class CreaturesDAO {
    private static CreaturesDAO instance = null;
    private DbHelper dbHelper;

    private CreaturesDAO(Context cntx){
        dbHelper = new DbHelper(cntx);
    }

    public static CreaturesDAO getInstance(Context cntx) {
        if (instance == null) {
            instance = new CreaturesDAO(cntx);
        }
        return instance;
    }

    public Boolean insert(Creature creature) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.TYPE_COLUMN, creature.getIdentity().getType());
        values.put(DbHelper.TITLE_COLUMN, creature.getIdentity().getTitle());
        values.put(DbHelper.DESCRIPTION_COLUMN, creature.getIdentity().getDescription());
        values.put(DbHelper.EXPERIENCE, creature.getExperience());
        values.put(DbHelper.LEVEL, creature.getLevel());
        values.put(DbHelper.GENDER, creature.getGender());
        values.put(DbHelper.RACE, creature.getRace());
        values.put(DbHelper.CREATURE_CLASS, creature.getCreatureClass());
        values.put(DbHelper.SUB_CLASS, creature.getSubClass());
        String attributes = DBUtils.listToDBString(creature.getAttributes());
        values.put(DbHelper.ATTRIBUTES, attributes);
        String stats = DBUtils.listToDBString(creature.getBaseStats());
        values.put(DbHelper.STATS, stats);
        String abilities = DBUtils.listToDBString(creature.getSpecialAbilities());
        values.put(DbHelper.SPECIAL_ABILITIES, abilities);
        String items = DBUtils.listToDBString(creature.getEquippedItems());
        values.put(DbHelper.EQUIPPED_ITEMS, items);
        String loot = DBUtils.listToDBString(creature.getAvailableLoot());
        values.put(DbHelper.AVAILABLE_LOOT, loot);

        try {
            Log.d("CreaturesDAO.insert", "Inserting Creature with id: " + creature.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper, DbHelper.TABLE_NAME_CREATURES);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("CreaturesDAO.insert", "Error: " + ex + " was thrown while inserting creature in DB.");
            return false;
        }
    }

    public boolean updateById(Creature creature) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(DbHelper.TYPE_COLUMN, creature.getIdentity().getType());
        values.put(DbHelper.TITLE_COLUMN, creature.getIdentity().getTitle());
        values.put(DbHelper.DESCRIPTION_COLUMN, creature.getIdentity().getDescription());
        values.put(DbHelper.EXPERIENCE, creature.getExperience());
        values.put(DbHelper.LEVEL, creature.getLevel());
        values.put(DbHelper.GENDER, creature.getGender());
        values.put(DbHelper.RACE, creature.getRace());
        values.put(DbHelper.CREATURE_CLASS, creature.getCreatureClass());
        values.put(DbHelper.SUB_CLASS, creature.getSubClass());
        values.put(DbHelper.ATTRIBUTES, creature.getAttributes().toString());
        values.put(DbHelper.STATS, creature.getBaseStats().toString());
        values.put(DbHelper.SPECIAL_ABILITIES, creature.getSpecialAbilities().toString());
        values.put(DbHelper.EQUIPPED_ITEMS, creature.getEquippedItems().toString());
        if(creature.getAvailableLoot() != null) {
            values.put(DbHelper.AVAILABLE_LOOT, creature.getAvailableLoot().toString());
        } else {
            values.put(DbHelper.AVAILABLE_LOOT, "");
        }
        if(creature.getCurrentCampaigns() != null) {
            values.put(DbHelper.CURRENT_CAMPAIGNS, creature.getCurrentCampaigns().toString());
        } else {
            values.put(DbHelper.CURRENT_CAMPAIGNS, "");
        }
        if(creature.getCurrentQuests() != null) {
            values.put(DbHelper.CURRENT_QUESTS, creature.getCurrentQuests().toString());
        } else {
            values.put(DbHelper.CURRENT_QUESTS, "");
        }
        if(creature.getCompletedCampaigns() != null) {
            values.put(DbHelper.COMPLETED_CAMPAIGNS, creature.getCompletedCampaigns().toString());
        } else {
            values.put(DbHelper.COMPLETED_CAMPAIGNS, "");
        }
        if(creature.getCompletedQuests() != null) {
            values.put(DbHelper.COMPLETED_QUESTS, creature.getCompletedQuests().toString());
        } else {
            values.put(DbHelper.COMPLETED_QUESTS, "");
        }

        Log.d("CreaturesDAO.updateById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + creature.getIdentity().getId());
        try {
            Log.d("CreaturesDAO.updateById", "Updating creature entry with Id " + creature.getIdentity().getId());
            UpdateEntryCallable task = new UpdateEntryCallable(values, dbHelper, DbHelper.TABLE_NAME_CREATURES);
            Future<Long> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("CreaturesDAO.updateById", "Error: " + ex + " was thrown while updating creature in DB.");
            return false;
        }
    }

    public long updateListById(List<Creature> chapters) {
        ListIterator<Creature> iterator = chapters.listIterator();
        int updateCount = 0;
        while (iterator.hasNext()) {
           Creature creature = iterator.next();
            if (updateById(creature)) {
            updateCount++;
            } else {
                Log.e("CreaturesDAO.updateListById", "An error was thrown while updating list of " +
                        "creatures in DB with creatureId: " + creature.getIdentity().getId());
                return ErrorCodes.DB_ERROR.getErrorCode();
            }
        }
        return updateCount;
    }

    public List<Creature> getAll()
            throws ExecutionException, InterruptedException {
        List<Creature> allCreatures = new ArrayList<Creature>();
        GetRowDataBySelection task = new GetRowDataBySelection(null, dbHelper, DbHelper.TABLE_NAME_CREATURES);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Creature creature = cursorToObject(cursor);
                allCreatures.add(creature);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("CreaturesDAO.getAll", "Error: ' " + ex +"' was thrown while processing all creatures.");
            return new ArrayList();
        } finally {
            result.get().close();
            cursor.close();
        }
        return allCreatures;
    }

    public List<Creature> getList(String selection)
            throws ExecutionException, InterruptedException {
        List<Creature> allCreatures = new ArrayList<Creature>();
        GetRowDataBySelection task = new GetRowDataBySelection(null, dbHelper, DbHelper.TABLE_NAME_CREATURES);
        Future<Cursor> result = ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
        Cursor cursor = result.get();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Creature creature = cursorToObject(cursor);
                allCreatures.add(creature);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("CreaturesDAO.getAll", "Error " + ex +" was thrown while processing all creatures.");
            return new ArrayList();
        } finally {
            result.get().close();
            cursor.close();
        }
        return allCreatures;
    }

    public void delete(Creature creature) {
        long id = creature.getIdentity().getId();
        String selection = DbHelper.ID_COLUMN + "=" + id;
        Log.d("CreaturesDAO.delete","About to delete DB entry: " + id + " in table: " + DbHelper.TABLE_NAME_CREATURES);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper, DbHelper.TABLE_NAME_CREATURES);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteList(List<Creature> creatures) {
        String selection = dbHelper.ID_COLUMN + " IN ";
        ListIterator<Creature> iterator = creatures.listIterator();
        while (iterator.hasNext()) {
            long id = iterator.next().getIdentity().getId();
            selection.concat(id + "','");
        }
        selection.concat(")");
        Log.d("CreaturesDAO.","About to delete DB entries with: " + selection + " in table: " + DbHelper.TABLE_NAME_CREATURES);
        DeleteRunnable task = new DeleteRunnable(selection, dbHelper, DbHelper.TABLE_NAME_CREATURES);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    public void deleteAll(){
        DeleteTableContentsRunnable task = new DeleteTableContentsRunnable(dbHelper, DbHelper.TABLE_NAME_CREATURES);
        ExecutorServiceProvider.getInstance().getDbExecutor().submit(task);
    }

    private Creature cursorToObject(Cursor cursor) {
        try {
            int id = cursor.getInt(0);
            Integer type = cursor.getInt(1);
            String title = cursor.getString(2);
            String description = cursor.getString(3);

            EntryIdentity identity = new EntryIdentity(id, type, title, description);

            int experience = cursor.getInt(4);
            int level = cursor.getInt(5);
            int gender= cursor.getInt(6);
            int creatureClass = cursor.getInt(7);
            int subClass = cursor.getInt(8);
            int race = cursor.getInt(9);
            List<Attribute> attributes = DBUtils.dbStringToListAttributeSet(cursor.getString(10));
            // TODO create DBUtils method
            // DBUtils.dbStringToListFloats(cursor.getString(11));
            Map<Integer, Float> stats = new HashMap<>();
            // TODO create DBUtils method
            // DBUtils.dbStringToListIntegers(cursor.getString(12));
            List<Ability> abilities = new ArrayList<>();
            List<Integer> items = DBUtils.dbStringToListIntegers(cursor.getString(13));
            List<Integer> loot = DBUtils.dbStringToListIntegers(cursor.getString(14));
            List<Integer> currentCampaigns = DBUtils.dbStringToListIntegers(cursor.getString(15));
            List<Integer> currentQuests = DBUtils.dbStringToListIntegers(cursor.getString(16));
            List<Integer> completedCampaigns = DBUtils.dbStringToListIntegers(cursor.getString(17));
            List<Integer> completedQuests = DBUtils.dbStringToListIntegers(cursor.getString(18));

            Creature creature = new Creature(identity, experience, level, gender, race, subClass,
                                        creatureClass, attributes, stats, abilities, items, loot,
                                        currentCampaigns, currentQuests, completedCampaigns, completedQuests);

            Log.d("CreaturesDAO.cursorToObject","Creating new creature with id " +
                    creature.getIdentity().getId() + ". Creature: " + creature);
            return creature;
        } catch (Exception ex) {
            Log.e("CreaturesDAO.cursorToObject", "Exception " + ex +" thrown while creating new creature");
            EntryIdentity errorIdentity = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(),"", "");
            Creature errorCreature = new Creature(errorIdentity);
            return errorCreature;
        }
    }

    public int getLastAvailableId() {
        return dbHelper.getLastAvailableCreatureId();
    }
}

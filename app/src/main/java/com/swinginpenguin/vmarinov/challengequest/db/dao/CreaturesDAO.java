package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.UpdateEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dao.runnable.DeleteTableContentsRunnable;
import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.CreatureDBHelper;
import com.swinginpenguin.vmarinov.challengequest.model.AttributeSet;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.db.utils.DBUtils;
import com.swinginpenguin.vmarinov.challengequest.db.dao.callable.InsertEntryCallable;
import com.swinginpenguin.vmarinov.challengequest.multithreading.executor.ExecutorServiceProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Future;

/**
 * Created by vmarinov on 11/11/2014.
 */
public class CreaturesDAO {

    private SQLiteDatabase database;
    private CreatureDBHelper dbHelper;

    public CreaturesDAO(Context cntx) {
        dbHelper = new CreatureDBHelper(cntx);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Boolean insert(Creature creature) {
        //TODO set Id in values!!!
        ContentValues values = new ContentValues();
        values.put(CreatureDBHelper.TYPE_COLUMN, creature.getIdentity().getType());
        values.put(CreatureDBHelper.TITLE_COLUMN, creature.getIdentity().getTitle());
        values.put(CreatureDBHelper.DESCRIPTION_COLUMN, creature.getIdentity().getDescription());
        values.put(CreatureDBHelper.EXPERIENCE, creature.getExperience());
        values.put(CreatureDBHelper.LEVEL, creature.getLevel());
        values.put(CreatureDBHelper.GENDER, creature.getGender());
        values.put(CreatureDBHelper.RACE, creature.getRace());
        values.put(CreatureDBHelper.CREATURE_CLASS, creature.getCreatureClass());
        values.put(CreatureDBHelper.SUB_CLASS, creature.getSubClass());
        String attributes = DBUtils.listToDBString(creature.getAttributes());
        values.put(CreatureDBHelper.ATTRIBUTES, attributes);
        String stats = DBUtils.listToDBString(creature.getBaseStats());
        values.put(CreatureDBHelper.STATS, stats);
        String abilities = DBUtils.listToDBString(creature.getSpecialAbilities());
        values.put(CreatureDBHelper.SPECIAL_ABILITIES, abilities);
        String items = DBUtils.listToDBString(creature.getEquippedItems());
        values.put(CreatureDBHelper.EQUIPPED_ITEMS, items);
        String loot = DBUtils.listToDBString(creature.getAvailableLoot());
        values.put(CreatureDBHelper.AVAILABLE_LOOT, loot);

        try {
            Log.d("CreaturesDAO.insert", "Inserting Creature with id: " + creature.getIdentity().getId());
            InsertEntryCallable insert = new InsertEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().dbExecutor.submit(insert);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("CreaturesDAO.insert", "Error: " + ex + " was thrown while inserting creature in DB.");
            return false;
        } finally {
            database.endTransaction();
        }
    }

    public boolean updateById(Creature creature) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(CreatureDBHelper.TYPE_COLUMN, creature.getIdentity().getType());
        values.put(CreatureDBHelper.TITLE_COLUMN, creature.getIdentity().getTitle());
        values.put(CreatureDBHelper.DESCRIPTION_COLUMN, creature.getIdentity().getDescription());
        values.put(CreatureDBHelper.EXPERIENCE, creature.getExperience());
        values.put(CreatureDBHelper.LEVEL, creature.getLevel());
        values.put(CreatureDBHelper.GENDER, creature.getGender());
        values.put(CreatureDBHelper.RACE, creature.getRace());
        values.put(CreatureDBHelper.CREATURE_CLASS, creature.getCreatureClass());
        values.put(CreatureDBHelper.SUB_CLASS, creature.getSubClass());
        values.put(CreatureDBHelper.ATTRIBUTES, creature.getAttributes().toString());
        values.put(CreatureDBHelper.STATS, creature.getBaseStats().toString());
        values.put(CreatureDBHelper.SPECIAL_ABILITIES, creature.getSpecialAbilities().toString());
        values.put(CreatureDBHelper.EQUIPPED_ITEMS, creature.getEquippedItems().toString());
        values.put(CreatureDBHelper.AVAILABLE_LOOT, creature.getAvailableLoot().toString());
        Log.d("CreaturesDAO.updateById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + creature.getIdentity().getId());
        try {
            Log.d("CreaturesDAO.updateById", "Updating creature entry with Id " + creature.getIdentity().getId());
            UpdateEntryCallable task = new UpdateEntryCallable(values, dbHelper);
            Future<Long> result = ExecutorServiceProvider.getInstance().dbExecutor.submit(task);

            return result.get() != -1;
        } catch (Exception ex) {
            Log.e("CreaturesDAO.updateById", "Error: " + ex + " was thrown while updating creature in DB.");
            return false;
        } finally {
            database.endTransaction();
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

    public List<Creature> getAll() {
        List<Creature> allCreatures = new ArrayList<Creature>();
        Cursor cursor = database.query(CreatureDBHelper.TABLE_NAME, null, null, null, null, null, null);
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
            cursor.close();
        }
        return allCreatures;
    }

    public void delete(Creature creature) {
        long id = creature.getIdentity().getId();
        DeleteRunnable task = new DeleteRunnable(id, dbHelper);
        ExecutorServiceProvider.getInstance().dbExecutor.submit(task);
    }

    public void deleteList(List<Creature> creatures) {
        ListIterator<Creature> iterator = creatures.listIterator();
        while (iterator.hasNext()) {
            delete(iterator.next());
        }
    }

    public void deleteAll(){
        DeleteTableContentsRunnable task = new DeleteTableContentsRunnable(dbHelper);
        ExecutorServiceProvider.getInstance().dbExecutor.submit(task);
    }

    private Creature cursorToObject(Cursor cursor) {
        try {
            long id = cursor.getPosition();
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
            List<AttributeSet> attributes = DBUtils.dbStringToListAttributeSet(cursor.getString(10));
            List<Float> stats = DBUtils.dbStringToListFloats(cursor.getString(11));
            List<Integer> abilities = DBUtils.dbStringToListIntegers(cursor.getString(12));
            List<Integer> items = DBUtils.dbStringToListIntegers(cursor.getString(13));
            List<Integer> loot = DBUtils.dbStringToListIntegers(cursor.getString(14));

            Creature creature = new Creature(identity, experience, level, gender, race, subClass,
                                        creatureClass, attributes, stats, abilities, items, loot);

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
}

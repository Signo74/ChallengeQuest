package com.swinginpenguin.vmarinov.challengequest.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dbhelper.CreatureDBHelper;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.ErrorCodes;
import com.swinginpenguin.vmarinov.challengequest.utils.DBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    public Creature insertCreature(int type, String title, String description, int experience,
                                  int level, int gender, int creatureClass, List<Integer> attributes,
                                  List<Float> stats, List<Integer> abilities, List<Integer> items,
                                  List<Integer> loot) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        values.put(dbHelper.TYPE_COLUMN, type);
        values.put(dbHelper.TITLE_COLUMN, title);
        values.put(dbHelper.DESCRIPTION_COLUMN, description);
        values.put(dbHelper.EXPERIENCE, experience);
        values.put(dbHelper.LEVEL, level);
        values.put(dbHelper.GENDER, gender);
        values.put(dbHelper.CREATURE_CLASS, creatureClass);
        values.put(dbHelper.ATTRIBUTES, attributes.toString());
        values.put(dbHelper.ATTRIBUTES, stats.toString());
        values.put(dbHelper.SPECIAL_ABILITIES, abilities.toString());
        values.put(dbHelper.EQUIPPED_ITEMS, items.toString());
        values.put(dbHelper.AVAILABLE_LOOT, loot.toString());

        try {
            database.beginTransaction();
            long insertID = database.insert(dbHelper.TABLE_NAME, null, values);
            Cursor cursor = database.query(dbHelper.TABLE_NAME, null, dbHelper.ID_COLUMN +
                    " = " + insertID, null, null, null, null);
            cursor.moveToFirst();
            cursor.close();
            database.setTransactionSuccessful();

            EntryIdentity identity = new EntryIdentity(insertID, type, title, description);
            Creature newCreature = new Creature(identity, experience, level, gender, creatureClass, attributes, stats, abilities, items, loot);

            return newCreature;
        } catch (Exception ex) {
            Log.e("CreaturesDAO.insertCreature", "Error: " + ex + " was thrown while inserting creature in DB.");
            EntryIdentity errorEntry = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(), "", "");
            Creature errorCreature = new Creature(errorEntry);
            return errorCreature;
        } finally {
            database.endTransaction();
        }
    }

    public boolean updateCreatureById(Creature creature) {
        //TODO replace .toString with DBUtils method
        ContentValues values = new ContentValues();
        int updateCount = 0;
        values.put(dbHelper.TYPE_COLUMN, creature.getIdentity().getType());
        values.put(dbHelper.TITLE_COLUMN, creature.getIdentity().getTitle());
        values.put(dbHelper.DESCRIPTION_COLUMN, creature.getIdentity().getDescription());
        values.put(dbHelper.EXPERIENCE, creature.getExperience());
        values.put(dbHelper.LEVEL, creature.getLevel());
        values.put(dbHelper.GENDER, creature.getGender());
        values.put(dbHelper.CREATURE_CLASS, creature.getCreatureClass());
        values.put(dbHelper.ATTRIBUTES, creature.getAttributes().toString());
        values.put(dbHelper.ATTRIBUTES, creature.getStats().toString());
        values.put(dbHelper.SPECIAL_ABILITIES, creature.getSpecialAbilities().toString());
        values.put(dbHelper.EQUIPPED_ITEMS, creature.getEquippedItems().toString());
        values.put(dbHelper.AVAILABLE_LOOT, creature.getAvailableLoot().toString());
        Log.d("CreaturesDAO.updateCreatureById", "Values " + values.valueSet() +
                " for updating existing entry with Id: " + creature.getIdentity().getId());
        database.beginTransaction();
        try {
            Log.d("CreaturesDAO.updateCreatureById", "Updating creature entry with Id " + creature.getIdentity().getId());
            updateCount = database.update(dbHelper.TABLE_NAME, values, dbHelper.ID_COLUMN + " = " + creature.getIdentity().getId(), null);
            database.setTransactionSuccessful();

        } catch (Exception ex) {
            Log.e("CreaturesDAO.updateCreatureById", "Error: " + ex + " was thrown while updating creature in DB.");
            return false;
        } finally {
            database.endTransaction();
        }

        return updateCount > 0;
    }

    public int updateListOfChaptersById(List<Creature> chapters) {
        //TODO replace .toString with DBUtils method
        ListIterator<Creature> iterator = chapters.listIterator();
        int updateCount = 0;
        database.beginTransaction();
        try {
            while (iterator.hasNext()) {
                Creature creature = iterator.next();
                ContentValues values = new ContentValues();
                values.put(dbHelper.TYPE_COLUMN, creature.getIdentity().getType());
                values.put(dbHelper.TITLE_COLUMN, creature.getIdentity().getTitle());
                values.put(dbHelper.DESCRIPTION_COLUMN, creature.getIdentity().getDescription());
                values.put(dbHelper.EXPERIENCE, creature.getExperience());
                values.put(dbHelper.LEVEL, creature.getLevel());
                values.put(dbHelper.GENDER, creature.getGender());
                values.put(dbHelper.CREATURE_CLASS, creature.getCreatureClass());
                values.put(dbHelper.ATTRIBUTES, creature.getAttributes().toString());
                values.put(dbHelper.ATTRIBUTES, creature.getStats().toString());
                values.put(dbHelper.SPECIAL_ABILITIES, creature.getSpecialAbilities().toString());
                values.put(dbHelper.EQUIPPED_ITEMS, creature.getEquippedItems().toString());
                values.put(dbHelper.AVAILABLE_LOOT, creature.getAvailableLoot().toString());
                Log.d("CreaturesDAO.updateListOfChaptersById", "Updating creature entry with id " +
                        creature.getIdentity().getId() + " with values {}" + values.valueSet());
                updateCount += database.update(dbHelper.TABLE_NAME, values, dbHelper.ID_COLUMN +
                        " = " + creature.getIdentity().getId(), null);
                database.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.e("CreaturesDAO.updateListOfChaptersById", "Error: " + ex +
                    " was thrown while updating list of creatures in DB.");
            return -1;
        } finally {
            database.endTransaction();
        }

        return updateCount;
    }

    public List<Creature> getAllCreatures() {
        List<Creature> allCreatures = new ArrayList<Creature>();
        Cursor cursor = database.query(dbHelper.TABLE_NAME, null, null, null, null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Creature creature = cursorToCreature(cursor);
                allCreatures.add(creature);
                cursor.moveToNext();
            }
        } catch (Exception ex) {
            Log.e("CreaturesDAO.getAllCreatures", "Error " + ex +" was thrown while processing all creatures.");
            return new ArrayList();
        } finally {
            cursor.close();
        }
        return allCreatures;
    }

    public void deleteCreature(Creature creature) {
        long id = creature.getIdentity().getId();
        database.beginTransaction();
        try {
            database.delete(dbHelper.TABLE_NAME, dbHelper.ID_COLUMN + "=" + id, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void deleteListOfCreatures(List<Creature> creatures) {
        ListIterator<Creature> iterator = creatures.listIterator();
        while (iterator.hasNext()) {
            deleteCreature(iterator.next());
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

    private Creature cursorToCreature(Cursor cursor) {
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
            List<Integer> attributes = DBUtils.dbStringToListIntegers(cursor.getString(8));
            List<Float> stats = DBUtils.dbStringToListFloats(cursor.getString(9));
            List<Integer> abilities = DBUtils.dbStringToListIntegers(cursor.getString(10));
            List<Integer> items = DBUtils.dbStringToListIntegers(cursor.getString(11));
            List<Integer> loot = DBUtils.dbStringToListIntegers(cursor.getString(12));

            Creature creature = new Creature(identity, experience, level, gender, creatureClass,
                    attributes, stats, abilities, items, loot);

            Log.d("CreaturesDAO.cursorToCreature","Creating new creature with id " +
                    creature.getIdentity().getId() + ". Creature: " + creature);
            return creature;
        } catch (Exception ex) {
            Log.e("CreaturesDAO.cursorToCreature", "Exception " + ex +" thrown while creating new creature");
            EntryIdentity errorIdentity = new EntryIdentity(-1, ErrorCodes.DB_ERROR.getErrorCode(),"", "");
            Creature errorCreature = new Creature(errorIdentity);
            return errorCreature;
        }
    }
}

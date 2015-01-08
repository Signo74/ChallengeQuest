package com.swinginpenguin.vmarinov.challengequest.db.utils;

import android.content.Context;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.CreaturesDAO;
import com.swinginpenguin.vmarinov.challengequest.model.Ability;
import com.swinginpenguin.vmarinov.challengequest.model.Attribute;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;
import com.swinginpenguin.vmarinov.challengequest.model.base.CreatureProperties;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by vmarinov on 11/13/2014.
 */
public class CreatureDBUtils {
    private CreaturesDAO dao;

    public CreatureDBUtils(Context ctx){
        dao = CreaturesDAO.getInstance(ctx);
    }

    public Creature quickAdd(int type, String title){
        Log.d("CreatureDBUtils.quickAdd","Adding creature with title: " + title);
        // TODO isn't this prone to error?
        int id = dao.getLastAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, "");
        int none = CreatureProperties.UNIDENTIFIED.getId();
        Creature dbEntry = new Creature(identity, 0, 1, none, none, none, none, null, null, null, null);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public Creature add(int type, String title, String description, int experience,
                       int level, int gender, int race, int creatureClass, int subClass,
                       List<Attribute> attributes, Map<Integer, Float> stats, List<Ability> abilities,
                       List<Integer> items, List<Integer> loot) {
        Log.d("CreatureDBUtils.quickAdd","Adding creature with title: " + title);
        // TODO isn't this prone to error?
        int id = dao.getLastAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, description);
        Creature dbEntry = new Creature(identity, experience, level, gender, race, creatureClass,
                                        subClass, attributes, stats, abilities, items);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public void deleteAll(){
        Log.d("CreatureDBUtils.deleteAll","Deleting all creatures from Data base");
        dao.deleteAll();
    }

    public List<Creature> getAll() {
        List<Creature> allItems = new ArrayList<>();
        try {
            allItems = dao.getAll();
            Log.d("CreatureDBUtils.getAll", "All creatures in DB: " + allItems);
        } catch (ExecutionException | InterruptedException ex){
            Log.e("CreatureDBUtils.getAll","Error: " + ex + " thrown while getting all quests from DB.");
        }

        return allItems;
    }
}

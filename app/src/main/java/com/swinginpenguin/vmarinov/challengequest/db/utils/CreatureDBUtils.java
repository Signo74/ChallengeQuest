package com.swinginpenguin.vmarinov.challengequest.db.utils;

import android.content.Context;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.CreaturesDAO;
import com.swinginpenguin.vmarinov.challengequest.model.AttributeSet;
import com.swinginpenguin.vmarinov.challengequest.model.Creature;

import java.util.List;

/**
 * Created by vmarinov on 11/13/2014.
 */
public class CreatureDBUtils {;

    private CreaturesDAO dao;

    public CreatureDBUtils(Context ctx){
        //TODO move creation of helper and databaseto DAO
        dao = new CreaturesDAO(ctx);
        try {
            dao.open();
        } catch (Exception ex ){
            Log.e("CreatureDBUtils","Error: " + ex + "while opening CreaturesDAO!");
        }
    }

    public Creature quickAdd(int type, String title){
        Log.d("CreatureDBUtils.quickAdd","Adding creature with title: " + title);
        return dao.insert(type, title, "", 0, 0, 0, 0, 0, 0, null, null, null, null, null);
    }

    public Creature add(int type, String title, String description, int experience,
                       int level, int gender, int race, int creatureClass, int subClass,
                       List<AttributeSet> attributes, List<Float> stats, List<Integer> abilities,
                       List<Integer> items, List<Integer> loot) {
        Log.d("CreatureDBUtils.quickAdd","Adding creature with title: " + title);
        Creature dbEntry = dao.insert(type, title, description, experience, level, gender, race,
                                    creatureClass, subClass, attributes, stats, abilities, items,
                                    loot);
        return dbEntry;
    }

    public void deleteAll(){
        Log.d("CreatureDBUtils.deleteAll","Deleting all creatures from Data base");
        dao.deleteAll();
    }

    public List<Creature> getAll(){
        List<Creature> childItemTitles = dao.getAll();
        Log.d("CreatureDBUtils.getAll", "All creatures in DB: " + childItemTitles);

        return childItemTitles;
    }
}

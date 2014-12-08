package com.swinginpenguin.vmarinov.challengequest.db.utils;

import android.content.Context;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.QuestsDAO;
import com.swinginpenguin.vmarinov.challengequest.model.Chapter;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryTypes;
import com.swinginpenguin.vmarinov.challengequest.model.utils.IdGenerator;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by victorm on 10/23/2014..
 */
public class QuestDBUtils {

    private final static Calendar dueDateCalendar = Calendar.getInstance();

    private QuestsDAO dao;

    public QuestDBUtils(Context ctx){
        dao = new QuestsDAO(ctx);
    }

    public Quest quickAdd(int type, String title){
        Log.d("QuestDBUtils.quickAdd","Adding quest with title: " + title);
        int id = IdGenerator.getInstance().getNextAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, "");
        Quest dbEntry = new Quest(identity, null, 0, 0, 0, 0);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public Quest add(int type, String title, String description, List<Chapter > chapters,
                     int experienceReward, int rank, int maxRank, int percentageCompleted) {
        Log.d("CreatureDBUtils.quickAdd","Adding creature with title: " + title);
        int id = IdGenerator.getInstance().getNextAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, description);
        Quest dbEntry = new Quest(identity, null, 0, 0, 0, 0);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public void deleteAll(){
        Log.d("QuestDBUtils.deleteAll","Deleting all tasks from Data base");
        dao.deleteAll();
    }

    public List<Quest> getAll()
            throws ExecutionException, InterruptedException {
        List<Quest> childItemTitles = dao.getAll();
        Log.d("QuestDBUtils.getAll","All quests in DB: " + childItemTitles);

        return childItemTitles;
    }
}
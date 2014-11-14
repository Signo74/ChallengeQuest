package com.swinginpenguin.vmarinov.challengequest.utils;

import android.content.Context;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.QuestsDAO;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryTypes;

import java.util.Calendar;
import java.util.List;

/**
 * Created by victorm on 10/23/2014..
 */
public class QuestDBUtils {

    private final static Calendar dueDateCalendar = Calendar.getInstance();

    private QuestsDAO dao;

    public QuestDBUtils(Context ctx){
        dao = new QuestsDAO(ctx);
        try {
            dao.open();
        } catch (Exception ex ){
            Log.e("QuestDBUtils","Error: " + ex + "while opening QuestsDAO!");
        }
    }

    public Quest quickAdd(String title){
        Log.d("QuestDBUtils.quickAddTask","Adding quest with title: " + title);
        return dao.insert(EntryTypes.QUEST.getId(), title, "", null, 0, 0, 0, 0);
    }

    public void deleteAll(){
        Log.d("QuestDBUtils.deleteAll","Deleting all tasks from Data base");
        dao.deleteAll();
    }

    public List<Quest> getAll(){
        List<Quest> childItemTitles = dao.getAll();
        Log.d("QuestDBUtils.getAll","All quests in DB: " + childItemTitles);

        return childItemTitles;
    }
}
package com.swinginpenguin.vmarinov.challengequest.utils;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.tasker.R;
import com.example.tasker.controller.dao.TasksDAO;
import com.example.tasker.model.CustomizationSettings;
import com.example.tasker.model.SharingSettings;
import com.example.tasker.model.Task;
import com.example.tasker.model.base.EntryTypes;
import com.swinginpenguin.vmarinov.challengequest.db.dao.QuestsDAO;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryTypes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by victorm on 10/23/2014..
 */
public class QuestDBUtils {

    private final static Calendar dueDateCalendar = Calendar.getInstance();

    public void QuestDBUtils() {
    }

    public Quest quickAddQuest(QuestsDAO dao, String title){
        Log.d("QuestDBUtils.quickAddTask","Adding quest with title: " + title);
        Quest quest = new Quest();
        return quest;
    }

    public void deleteAllQuests(QuestsDAO dao){
        Log.d("QuestDBUtils.deleteAllQuests","Deleting all tasks from Data base");
        dao.deleteAll();
    }

    public List<Quest> getAllQuests(QuestsDAO dao){
        List<Quest> childItemTitles = dao.getAllTasks();
        Log.d("QuestDBUtils.getAllQuests","All quests in DB: " + childItemTitles);

        return childItemTitles;
    }

}
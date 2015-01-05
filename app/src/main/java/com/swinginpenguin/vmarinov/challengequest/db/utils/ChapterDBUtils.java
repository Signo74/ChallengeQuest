package com.swinginpenguin.vmarinov.challengequest.db.utils;

import android.content.Context;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.ChaptersDAO;
import com.swinginpenguin.vmarinov.challengequest.model.Chapter;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by vmarinov on 09-Dec-14.
 */
public class ChapterDBUtils {
    private ChaptersDAO dao;
    public ChapterDBUtils(Context ctx){
        dao = new ChaptersDAO(ctx);
    }

    public Chapter quickAdd(int type, String title){
        Log.d("ChapterDBUtils.quickAdd", "Adding chapter with title: " + title);
        int id = dao.getLastAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, "");

        Chapter dbEntry = new Chapter(identity, 0, 0, 0, 0, 0);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public Chapter add(int type, String title, String description, int experienceReward, int rank,
                       int maxRank, long record, int percentageCompleted) {
        Log.d("ChapterDBUtils.quickAdd","Adding chapter with title: " + title);
        int id = dao.getLastAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, description);
        Chapter dbEntry = new Chapter(identity, experienceReward, rank, maxRank, record, percentageCompleted);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public void deleteAll(){
        Log.d("ChapterDBUtils.deleteAll","Deleting all chapters from Data base");
        dao.deleteAll();
    }

    public List<Chapter> getAll() {
        List<Chapter> allItems = new ArrayList<>();
        try {
            allItems = dao.getAll();
            Log.d("ChapterDBUtils.getAll", "All chapters in DB: " + allItems);
        } catch (ExecutionException | InterruptedException ex){
            Log.e("ChapterDBUtils.getAll","Error: " + ex + " thrown while getting all chapter from DB.");
        }

        return allItems;
    }
}

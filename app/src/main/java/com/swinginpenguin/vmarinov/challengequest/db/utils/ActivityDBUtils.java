package com.swinginpenguin.vmarinov.challengequest.db.utils;

        import android.content.Context;
        import android.util.Log;

        import com.swinginpenguin.vmarinov.challengequest.db.dao.ActivityDAO;
        import com.swinginpenguin.vmarinov.challengequest.model.Activity;
        import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.ExecutionException;

/**
 * Created by victorm on 10/23/2014..
 */
public class ActivityDBUtils {
    private ActivityDAO dao;
    public ActivityDBUtils(Context ctx){
        dao = ActivityDAO.getInstance(ctx);
    }

    public Activity quickAdd(int type, String title){
        Log.d("ActivityDBUtils.quickAdd","Adding quest with title: " + title);
        int id = dao.getLastAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, "");
        Activity dbEntry = new Activity(identity, 0, 0, 0, 0, null, null);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public Activity add(int type, String title, String description,
                     int experienceReward, int rank, int maxRank, int percentageCompleted,
                     List<Activity> quests, List<Activity> chapters) {
        Log.d("CreatureDBUtils.quickAdd","Adding quest with title: " + title);
        int id = dao.getLastAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, description);
        Activity dbEntry = new Activity(identity,0, 0, 0, 0, quests, chapters);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public void deleteAll(){
        Log.d("ActivityDBUtils.deleteAll","Deleting all tasks from Data base");
        dao.deleteAll();
    }

    public List<Activity> getAll(Integer type) {
        List<Activity> allItems = new ArrayList<>();
        try {
            allItems = dao.getAll(type);
            Log.d("ActivityDBUtils.getAll", "All quests in DB: " + allItems);
        } catch (ExecutionException | InterruptedException ex) {
            Log.e("ActivityDBUtils.getAll","Error: " + ex + " thrown while getting all quests from DB.");
            //TODO handle exception
        }

        return allItems;
    }
}
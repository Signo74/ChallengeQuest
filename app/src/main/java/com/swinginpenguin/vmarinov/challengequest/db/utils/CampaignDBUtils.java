package com.swinginpenguin.vmarinov.challengequest.db.utils;

import android.content.Context;
import android.util.Log;

import com.swinginpenguin.vmarinov.challengequest.db.dao.CampaignDAO;
import com.swinginpenguin.vmarinov.challengequest.model.Campaign;
import com.swinginpenguin.vmarinov.challengequest.model.Quest;
import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;
import com.swinginpenguin.vmarinov.challengequest.model.utils.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by vmarinov on 09-Dec-14.
 */
public class CampaignDBUtils {
    private CampaignDAO dao;
    public CampaignDBUtils(Context ctx){
        dao = new CampaignDAO(ctx);
    }

    public Campaign quickAdd(int type, String title){
        Log.d("CampaignDBUtils.quickAdd", "Adding campaign with title: " + title);
        int id = dao.getLastAvailableId();
        EntryIdentity identity = new EntryIdentity(id, type, title, "");

        Campaign dbEntry = new Campaign(identity, 0, 0, 0, 0, 0, null);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public Campaign add(int type, String title, String description, int experienceReward, int rank,
                       int maxRank, long record, int percentageCompleted, List<Quest> quests) {
        Log.d("CampaignDBUtils.quickAdd","Adding campaign with title: " + title);
        int id = dao.getLastAvailableId();;
        EntryIdentity identity = new EntryIdentity(id, type, title, description);
        Campaign dbEntry = new Campaign(identity, experienceReward, rank, maxRank, record, percentageCompleted, quests);
        if (!dao.insert(dbEntry)) {
            //TODO handle false insertion.
        }
        return dbEntry;
    }

    public void deleteAll(){
        Log.d("CampaignDBUtils.deleteAll","Deleting all campaigns from Data base");
        dao.deleteAll();
    }

    public List<Campaign> getAll() {
        List<Campaign> allItems = new ArrayList<>();
        try {
            allItems = dao.getAll();
            Log.d("CampaignDBUtils.getAll", "All campaigns in DB: " + allItems);
        } catch (ExecutionException | InterruptedException ex){
            Log.e("CampaignDBUtils.getAll","Error: " + ex + " thrown while getting all campaign from DB.");
        }

        return allItems;
    }
}

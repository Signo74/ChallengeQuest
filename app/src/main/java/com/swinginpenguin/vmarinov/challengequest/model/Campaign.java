package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 * @quests: a list of all the quests that are contained in this campaign.
 * A quest on the other side can be consistent of one or more chapters.
 * For Example The campaign run 30k in 30 days may consist of 30 quests each made up
 * from one chapter to run 1k a day. Or it may be contain 5 quests each having 6 chapters
 * to run 1k a day.
 */
public class Campaign {
    //TODO create and implement ICampaignBehaviour
    //TODO or create a Campaign controller and delegate quest functionality to it.
    private EntryIdentity identity;
    private int experienceReward;
    private int rank;
    private int maxRank;
    private long record;
    private int percentageCompleted;
    private List<Quest> quests;

    public Campaign(EntryIdentity identity) {
        this.identity = identity;
    }

    public Campaign(EntryIdentity identity, int experienceReward, int rank, int maxRank, long record, int percentageCompleted, List<Quest> quests) {
        this.identity = identity;
        this.quests = quests;
        this.experienceReward = experienceReward;
        this.percentageCompleted = percentageCompleted;
        this.rank = rank;
        this.maxRank = maxRank;
    }

    public EntryIdentity getIdentity() {
        return identity;
    }

    public int getExperienceReward() {
        return experienceReward;
    }

    public void setExperienceReward(int experienceReward) {
        this.experienceReward = experienceReward;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(int maxRank) {
        this.maxRank = maxRank;
    }

    public long getRecord() {
        return record;
    }

    public void setRecord(long record) {
        this.record = record;
    }

    public int getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(int percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "identity=" + identity +
                ", experienceReward=" + experienceReward +
                ", rank=" + rank +
                ", maxRank=" + maxRank +
                ", record=" + record +
                ", percentageCompleted=" + percentageCompleted +
                ", quests=" + quests +
                '}';
    }
}

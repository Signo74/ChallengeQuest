package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Activity {
    //TODO create and implement IQuestBehaviour
    //TODO or create a Quest controller and delegate quest functionality to it.
    private EntryIdentity identity;
    private int experienceReward;
    private int rank;
    private int maxRank;
    private int percentageCompleted;
    // Can be null!
    private List<Activity> quests;
    // Can be null!
    private List<Activity> chapters;

    public Activity(EntryIdentity identity) {
        this.identity = identity;
    }

    public Activity(EntryIdentity identity, int experienceReward, int rank, int maxRank,
                    int percentageCompleted, List<Activity> quests, List<Activity> chapters) {
        this.identity = identity;
        this.quests = quests;
        this.chapters = chapters;
        this.experienceReward = experienceReward;
        this.rank = rank;
        this.maxRank = maxRank;
        this.percentageCompleted = percentageCompleted;
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

    public int getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(int percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    public List<Activity> getQuests() {
        return quests;
    }

    public void setQuests(List<Activity> quests) {
        this.quests = quests;
    }

    public List<Activity> getChapters() {
        return chapters;
    }

    public void setChapters(List<Activity> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "identity=" + identity +
                ", experienceReward=" + experienceReward +
                ", rank=" + rank +
                ", maxRank=" + maxRank +
                ", percentageCompleted=" + percentageCompleted +
                ", quests=" + quests +
                ", chapters=" + chapters +
                '}';
    }
}
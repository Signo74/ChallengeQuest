package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Quest {
    //TODO create and implement IQuestBehaviour
    //TODO or create a Quest controller and delegate quest functionality to it.
    private EntryIdentity identity;
    // Can be null!
    private List<Chapter> chapters;
    private int experienceReward;
    private int rank;
    private int maxRank;
    private int percentageCompleted;

    public Quest(EntryIdentity identity) {
        this.identity = identity;
    }

    public Quest(EntryIdentity identity, List<Chapter> chapters, int experienceReward, int rank, int maxRank, int percentageCompleted) {
        this.identity = identity;
        this.chapters = chapters;
        this.experienceReward = experienceReward;
        this.rank = rank;
        this.maxRank = maxRank;
        this.percentageCompleted = percentageCompleted;
    }

    public EntryIdentity getIdentity() {
        return identity;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
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

    @Override
    public String toString() {
        return "Quest{" +
                "percentageCompleted=" + percentageCompleted +
                ", maxRank=" + maxRank +
                ", rank=" + rank +
                ", experienceReward=" + experienceReward +
                ", chapters=" + chapters +
                ", identity=" + identity +
                '}';
    }
}
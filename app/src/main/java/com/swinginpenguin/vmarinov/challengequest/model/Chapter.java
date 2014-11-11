package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Chapter {
    //TODO create and implement IChapterBehaviour
    //TODO or create a Chapter controller and delegate quest functionality to it.
    private EntryIdentity identity;
    private int experienceReward;
    private int rank;
    private int maxRank;
    private long record;
    private int percentageCompleted;

    public Chapter(EntryIdentity identity) {
        this.identity = identity;
    }

    public Chapter(EntryIdentity identity, int experienceReward, int rank, int maxRank, long record, int percentageCompleted) {
        this.identity = identity;
        this.experienceReward = experienceReward;
        this.percentageCompleted = percentageCompleted;
        this.rank = rank;
        this.maxRank = maxRank;
        this.record = record;
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

    public int getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(int percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
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

    @Override
    public String toString() {
        return "Chapter{" +
                "identity=" + identity +
                ", experienceReward=" + experienceReward +
                ", rank=" + rank +
                ", maxRank=" + maxRank +
                ", record=" + record +
                ", percentageCompleted=" + percentageCompleted +
                '}';
    }
}
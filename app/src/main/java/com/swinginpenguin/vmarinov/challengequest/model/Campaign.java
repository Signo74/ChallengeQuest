package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Campaign {
    private EntryIdentity identity;
    private List<Quest> quests;
    private int experienceReward;
    private int percentageCompleted;
    private int rank;
    private int maxRank;

    public Campaign(EntryIdentity identity) {
        this.identity = identity;
    }

    public Campaign(EntryIdentity identity, List<Quest> quests, int experienceReward, int percentageCompleted, int rank, int maxRank) {
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

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
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

    public int getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(int maxRank) {
        this.maxRank = maxRank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "identity=" + identity +
                ", quests=" + quests +
                ", experienceReward=" + experienceReward +
                ", percentageCompleted=" + percentageCompleted +
                '}';
    }
}

package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Chapter {
    private EntryIdentity identity;
    private int experienceGranted;
    private int percentageCompleted;
    private int rank;
    private int possibleRank;
    private long record;

    public Chapter(EntryIdentity identity, int experienceGranted, int percentageCompleted, int rank, int possibleRank, long record) {
        this.identity = identity;
        this.experienceGranted = experienceGranted;
        this.percentageCompleted = percentageCompleted;
        this.rank = rank;
        this.possibleRank = possibleRank;
        this.record = record;
    }


}
package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Quest {
    private EntryIdentity identity;
    private List<Chapter> chapters;
    private int experienceGranted;
    private int rank;
    private int possibleRank;
    private int percentageCompleted;
}
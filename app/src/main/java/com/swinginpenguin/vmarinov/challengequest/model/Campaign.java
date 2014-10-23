package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Campaign {
    private EntryIdentity identity;
    private List<Quest> quests;
    private int experienceGranted;
    private int percentageCompleted;
}

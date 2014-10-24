package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/23/2014.
 */
public class Item {
    private EntryIdentity identity;
    private boolean equipped;
    // Str, Dex, Stam, Cha, Const ...
    private List<Integer> attributes;
    // Health, speed, crit, hit ...
    private List<Float> stats;
    // steal health, armor penetration
    private List<Float> specialAttributes;
}
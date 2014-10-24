package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.util.List;

/**
 * Created by vmarinov on 10/24/2014.
 */
public class Creature {
    private EntryIdentity identity;
    // Str, Dex, Stam, Cha, Const ...
    private List<Integer> attributes;
    // Health, speed, crit, hit ...
    private List<Float> stats;
    // Heal, spells, stealth ....
    private List<Integer> specialAbilities;
}

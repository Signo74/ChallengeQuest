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

    public Creature(EntryIdentity identity) {
        this.identity = identity;
    }

    public Creature(EntryIdentity identity, List<Integer> attributes, List<Float> stats, List<Integer> specialAbilities) {
        this.identity = identity;
        this.attributes = attributes;
        this.stats = stats;
        this.specialAbilities = specialAbilities;
    }

    public EntryIdentity getIdentity() {
        return identity;
    }

    public List<Integer> getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(List<Integer> specialAbilities) {
        this.specialAbilities = specialAbilities;
    }

    public List<Float> getStats() {
        return stats;
    }

    public void setStats(List<Float> stats) {
        this.stats = stats;
    }

    public List<Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Integer> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "identity=" + identity +
                ", attributes=" + attributes +
                ", stats=" + stats +
                ", specialAbilities=" + specialAbilities +
                '}';
    }
}

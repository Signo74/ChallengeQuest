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

    public Item(EntryIdentity identity) {
        this.identity = identity;
    }

    public Item(EntryIdentity identity, boolean equipped, List<Integer> attributes, List<Float> stats, List<Float> specialAttributes) {
        this.identity = identity;
        this.equipped = equipped;
        this.attributes = attributes;
        this.stats = stats;
        this.specialAttributes = specialAttributes;
    }

    public EntryIdentity getIdentity() {
        return identity;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public List<Integer> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Integer> attributes) {
        this.attributes = attributes;
    }

    public List<Float> getStats() {
        return stats;
    }

    public void setStats(List<Float> stats) {
        this.stats = stats;
    }

    public List<Float> getSpecialAttributes() {
        return specialAttributes;
    }

    public void setSpecialAttributes(List<Float> specialAttributes) {
        this.specialAttributes = specialAttributes;
    }

    @Override
    public String toString() {
        return "Item{" +
                "identity=" + identity +
                ", equipped=" + equipped +
                ", attributes=" + attributes +
                ", stats=" + stats +
                ", specialAttributes=" + specialAttributes +
                '}';
    }
}
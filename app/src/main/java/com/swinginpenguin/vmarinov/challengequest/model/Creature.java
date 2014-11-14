package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vmarinov on 10/24/2014.
 */
public class Creature implements Serializable{
    //TODO create and implement ICreatureBehavior
    //TODO or create a creature controller and delegate all behavior of the object to it.
    private EntryIdentity identity;

    private int experience;
    private int level;
    private int gender;
    private int race;
    private int creatureClass;
    private int subClass;

    // Str, Dex, Stam, Cha, Const ...
    private List<AttributeSet> attributes;
    // Initial value forHealth, speed, crit, hit ... before applying attribute bonuses
    private List<Float> baseStats;
    // Heal, spells, stealth ....
    private List<Integer> specialAbilities;

    private List<Integer> equippedItems;
    private List<Integer> availableLoot;

    public Creature(EntryIdentity identity) {
        this.identity = identity;
    }

    public Creature(EntryIdentity identity, int experience, int level, int gender, int race,
                    int creatureClass, int subClass, List<AttributeSet> attributes,
                    List<Float> stats, List<Integer> specialAbilities, List<Integer> equippedItems,
                    List<Integer> availableLoot) {
        this.identity = identity;
        this.experience = experience;
        this.level = level;
        this.gender = gender;
        this.race = race;
        this.creatureClass = creatureClass;
        this.subClass = subClass;
        this.attributes = attributes;
        this.baseStats = stats;
        this.specialAbilities = specialAbilities;
        this.equippedItems = equippedItems;
        this.availableLoot = availableLoot;
    }

    public EntryIdentity getIdentity() {
        return identity;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public int getCreatureClass() {
        return creatureClass;
    }

    public void setCreatureClass(int creatureClass) {
        this.creatureClass = creatureClass;
    }

    public int getSubClass() {
        return subClass;
    }

    public void setSubClass(int subClass) {
        this.subClass = subClass;
    }

    public List<AttributeSet> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeSet> attributes) {
        this.attributes = attributes;
    }

    public void setBaseStats(List<Float> baseStats) {
        this.baseStats = baseStats;
    }

    public List<Float> getBaseStats() {
        return baseStats;
    }

    public List<Integer> getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(List<Integer> specialAbilities) {
        this.specialAbilities = specialAbilities;
    }

    public List<Integer> getEquippedItems() {
        return equippedItems;
    }

    public void setEquippedItems(List<Integer> equippedItems) {
        this.equippedItems = equippedItems;
    }

    public List<Integer> getAvailableLoot() {
        return availableLoot;
    }

    public void setAvailableLoot(List<Integer> availableLoot) {
        this.availableLoot = availableLoot;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "identity=" + identity +
                ", experience=" + experience +
                ", level=" + level +
                ", gender=" + gender +
                ", creatureClass=" + creatureClass +
                ", attributes=" + attributes +
                ", baseStats=" + baseStats +
                ", specialAbilities=" + specialAbilities +
                ", equippedItems=" + equippedItems +
                ", availableLoot=" + availableLoot +
                '}';
    }
}

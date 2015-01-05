package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
    private List<Attribute> attributes;
    // Initial value forHealth, speed, crit, hit ... before applying attribute bonuses
    private Map<String, Float> baseStats;
    // Heal, spells, stealth ....
    private List<Ability> specialAbilities;

    private List<Integer> equippedItems;
    private List<Integer> availableLoot;
    private List<Integer> currentCampaigns;
    private List<Integer> currentQuests;
    private List<Integer> completedCampaigns;
    private List<Integer> completedQuests;

    public Creature(EntryIdentity identity) {
        this.identity = identity;
    }

    public Creature(EntryIdentity identity, int experience, int level, int gender, int race,
                    int creatureClass, int subClass, List<Attribute> attributes,
                    Map<String, Float> baseStats, List<Ability> specialAbilities,
                    List<Integer> equippedItems) {
        this.identity = identity;
        this.experience = experience;
        this.level = level;
        this.gender = gender;
        this.race = race;
        this.creatureClass = creatureClass;
        this.subClass = subClass;
        this.attributes = attributes;
        this.baseStats = baseStats;
        this.specialAbilities = specialAbilities;
        this.equippedItems = equippedItems;
    }

    public Creature(EntryIdentity identity, int experience, int level, int gender, int race,
                    int creatureClass, int subClass, List<Attribute> attributes,
                    Map<String, Float> baseStats, List<Ability> specialAbilities,
                    List<Integer> equippedItems, List<Integer> availableLoot,
                    List<Integer> currentCampaigns, List<Integer> currentQuests,
                    List<Integer> completedCampaigns, List<Integer> completedQuests) {
        this.identity = identity;
        this.experience = experience;
        this.level = level;
        this.gender = gender;
        this.race = race;
        this.creatureClass = creatureClass;
        this.subClass = subClass;
        this.attributes = attributes;
        this.baseStats = baseStats;
        this.specialAbilities = specialAbilities;
        this.equippedItems = equippedItems;
        this.availableLoot = availableLoot;
        this.currentCampaigns = currentCampaigns;
        this.currentQuests = currentQuests;
        this.completedCampaigns = completedCampaigns;
        this.completedQuests = completedQuests;
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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setBaseStats(Map<String, Float> baseStats) {
        this.baseStats = baseStats;
    }

    public Map<String, Float> getBaseStats() {
        return baseStats;
    }

    public List<Ability> getSpecialAbilities() {
        return specialAbilities;
    }

    public void setSpecialAbilities(List<Ability> specialAbilities) {
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

    public List<Integer> getCurrentCampaigns() {
        return currentCampaigns;
    }

    public void setCurrentCampaigns(List<Integer> currentCampaigns) {
        this.currentCampaigns = currentCampaigns;
    }

    public List<Integer> getCurrentQuests() {
        return currentQuests;
    }

    public void setCurrentQuests(List<Integer> currentQuests) {
        this.currentQuests = currentQuests;
    }

    public List<Integer> getCompletedCampaigns() {
        return completedCampaigns;
    }

    public void setCompletedCampaigns(List<Integer> completedCampaigns) {
        this.completedCampaigns = completedCampaigns;
    }

    public List<Integer> getCompletedQuests() {
        return completedQuests;
    }

    public void setCompletedQuests(List<Integer> completedQuests) {
        this.completedQuests = completedQuests;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "identity=" + identity +
                ", experience=" + experience +
                ", level=" + level +
                ", gender=" + gender +
                ", race=" + race +
                ", creatureClass=" + creatureClass +
                ", subClass=" + subClass +
                ", attributes=" + attributes +
                ", baseStats=" + baseStats +
                ", specialAbilities=" + specialAbilities +
                ", equippedItems=" + equippedItems +
                ", availableLoot=" + availableLoot +
                ", currentCampaigns=" + currentCampaigns +
                ", currentQuests=" + currentQuests +
                ", completedCampaigns=" + completedCampaigns +
                ", completedQuests=" + completedQuests +
                '}';
    }
}

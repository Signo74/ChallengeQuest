package com.swinginpenguin.vmarinov.challengequest.model;

import java.io.Serializable;

/**
 * Created by viktor.marinov on 1/5/2015.
 */
public class Ability implements Serializable {
    private String name;
    private int level;
    // The name of the affected attribute / stat.
    // Can be null!
    private String affectedCharacterProperty;
    // The amount by which the ability affects the attribute/stat.
    //Can be null.
    private float effectPercentage;
    // The duration of the effect in milliseconds.
    // Can be null!
    private int duration;
    // The amount of damage dealt.
    // Can be null! Can be negative in case of healing.
    private int damage;
    // The radius of effect of the ability in fictional meters.
    // Can't be null, only 0;
    private int radius;
    // The target of the creature on which it will be used.
    // Can't be null, -1 if target is self.
    private int targetCreatureId;

    public Ability(String name, int level, String affectedCharacterProperty, float effectPercentage, int duration, int damage, int radius, int targetCreatureId) {
        this.name = name;
        this.level = level;
        this.affectedCharacterProperty = affectedCharacterProperty;
        this.effectPercentage = effectPercentage;
        this.duration = duration;
        this.damage = damage;
        this.radius = radius;
        this.targetCreatureId = targetCreatureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAffectedCharacterProperty() {
        return affectedCharacterProperty;
    }

    public void setAffectedCharacterProperty(String affectedCharacterProperty) {
        this.affectedCharacterProperty = affectedCharacterProperty;
    }

    public float getEffectPercentage() {
        return effectPercentage;
    }

    public void setEffectPercentage(float effectPercentage) {
        this.effectPercentage = effectPercentage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getTargetCreatureId() {
        return targetCreatureId;
    }

    public void setTargetCreatureId(int targetCreatureId) {
        this.targetCreatureId = targetCreatureId;
    }

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", affectedCharacterProperty='" + affectedCharacterProperty + '\'' +
                ", effectPercentage=" + effectPercentage +
                ", duration=" + duration +
                ", damage=" + damage +
                ", radius=" + radius +
                ", targetCreatureId=" + targetCreatureId +
                '}';
    }
}

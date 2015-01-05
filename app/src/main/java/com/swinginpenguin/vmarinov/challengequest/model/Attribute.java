package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by vmarinov on 11/12/2014.
 */
public class Attribute implements Serializable {
    private String name;
    // The value of the attribute
    private int value;
    // Map of affected stats and the percentage by which this attributes affects them per each
    // attribute point. Negative / debilitating effects are achieved by lowering the attribute.
    private Map<String, Float> statsEffect;

    public Attribute(String name, int value, Map<String, Float> statsEffect) {
        this.name = name;
        this.value = value;
        this.statsEffect = statsEffect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Map<String, Float> getStatsEffect() {
        return statsEffect;
    }

    public void setStatsEffect(Map<String, Float> statsEffect) {
        this.statsEffect = statsEffect;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "statsEffect=" + statsEffect +
                ", value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.swinginpenguin.vmarinov.challengequest.model;

import com.swinginpenguin.vmarinov.challengequest.model.base.EntryIdentity;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by vmarinov on 11/12/2014.
 */
public class AttributeSet implements Serializable {
    private EntryIdentity identity;
    // The value of the attribute
    private int value;
    // Map of affected stats and the percentage by which this attributes affects them
    // NB: effect can be negative!
    private Map<Integer, Integer> statsEffect;
    //TODO implement all internals
    //TODO override toString()!!!
}

package com.swinginpenguin.vmarinov.challengequest.model.base;

/**
 * Created by vmarinov on 11/12/2014.
 */
public enum CreaturesTypes {
    PLAYER(1),
    NPC(2),
    ENEMY(3);

    private int creatureId;

    CreaturesTypes(int value) {
        this.creatureId = value;
    }

    public int getId() {
        return creatureId;
    }
}

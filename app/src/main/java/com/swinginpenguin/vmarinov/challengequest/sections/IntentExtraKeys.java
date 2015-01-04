package com.swinginpenguin.vmarinov.challengequest.sections;

/**
 * Created by Mi6 on 27-Dec-14.
 */
public enum IntentExtraKeys {
    PLAYER_HERO_EXTRA("playerHero"),

    //TODO remove this when finalized.
    END("end");

    private String propertyId;

    IntentExtraKeys(String value) {
        this.propertyId = value;
    }

    public String getKey() {
        return propertyId;
    }
}

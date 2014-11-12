package com.swinginpenguin.vmarinov.challengequest.model.base;

/**
 * Created by vmarinov on 11/12/2014.
 */
public enum CreatureProperties {
    // 1-> 100 properties and attributes
    MALE(1),
    FEMALE(2),
    // 100+ Classes
    FIGHTER(101),
    WIZARD(102),
    ROUGE(103),
    MONK(104),

    // 200+ attributes
    STRENGTH(201),
    DEXTERITY(202),
    STAMINA(203),
    WISDOM(204),
    INTELLIGENCE(205),
    CHARISMA(206),

    // 300+ stats
    HEALTH(301),
    LUCK(302),
    SPEED(303),
    BASE_DAMAGE(304),
    BASE_DEFENSE(305),
    HIT_CHANCE_MELEE(306),
    HIT_CHANCE_RANGED(307),
    CRITICAL_HIT_CHANCE(308),
    CRITICAL_HIT_DAMAGE(309),

    //TODO remove this when finalized.
    END(-1);

    private int propertyId;

    CreatureProperties(int value) {
        this.propertyId = value;
    }

    public int getId() {
        return propertyId;
    }
}

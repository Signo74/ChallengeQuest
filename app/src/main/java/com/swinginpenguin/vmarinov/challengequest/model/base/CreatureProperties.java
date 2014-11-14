package com.swinginpenguin.vmarinov.challengequest.model.base;

/**
 * Created by vmarinov on 11/12/2014.
 */
public enum CreatureProperties {
    // 1-> 100 properties and attributes
    MALE(1),
    FEMALE(2),
    // 100+ Races
    //+1 action point
    HUMAN(101),
    //+1 intelligence action point
    ELF(102),
    //+1 physical action point
    DWARF(103),
    //+2 intelligence action points -1 physical action point
    GNOME(104),
    //+2 physical -1 intelligence
    GIANT(105),

    // 200+ Classes
    FIGHTER(201),
    WIZARD(202),
    ROUGE(203),
    MONK(204),
    CLERIC(205),

    // 250+ Subclasses
    BRAND_NEW_HERO(250),

    // 300+ attributes
    STRENGTH(301),
    DEXTERITY(302),
    STAMINA(303),
    WISDOM(304),
    INTELLIGENCE(305),
    CHARISMA(306),

    // 400+ stats
    HEALTH(401),
    LUCK(402),
    SPEED(403),
    BASE_DAMAGE(404),
    BASE_DEFENSE(405),
    HIT_CHANCE_MELEE(406),
    HIT_CHANCE_RANGED(407),
    CRITICAL_HIT_CHANCE(408),
    CRITICAL_HIT_DAMAGE(409),

    // 1000+ Badges - earned when completing certain type of quests
    BLACKSMITH(1000),
    CHIEFTAIN(1001),
    MASTER_CHEF(1002),
    HERBALIST(1003),
    LIBRARIAN(1004),


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

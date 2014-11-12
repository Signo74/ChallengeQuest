package com.swinginpenguin.vmarinov.challengequest.model.base;

/**
 * Created by vmarinov on 10/23/2014.
 */
public enum EntryTypes {
    // 1-100 base entries
    CHAPTER(1),
    QUEST(2),
    CAMPAIGN(3),
    IMAGE(4),
    TODO_LIST(5),
    SETTINGS_ITEM(6),

    // Settings item types -> 100+
    SETTINGS_ITEM_CHECKBOX(100),
    SETTINGS_ITEM_RADIO(101),
    SETTINGS_ITEM_COMBO(102),
    SETTINGS_ITEM_BUTTON(103),
    SETTINGS_ITEM_DIALOG(104),
    SETTINGS_ITEM_STRING_VALUE(105);

    private int entryId;

    EntryTypes(int value) {
        this.entryId = value;
    }

    public int getId() {
        return entryId;
    }
}
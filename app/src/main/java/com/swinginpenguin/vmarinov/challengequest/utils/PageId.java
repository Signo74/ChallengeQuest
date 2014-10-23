package com.swinginpenguin.vmarinov.challengequest.utils;

/**
 * Created by vmarinov on 10/23/2014.
 */
public enum PageId {
    PAGE_ID(0);
    //TODO find a purpose for this one or delete it!

    private final int pageId;

    PageId(int value) {
        this.pageId = value;
    }

    public int getPageId() {
        return pageId;
    }

}

package com.swinginpenguin.vmarinov.challengequest.model.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vmarinov on 11/24/2014.
 */
public class IdGenerator
{
    private static IdGenerator ourInstance = new IdGenerator();
    private static AtomicInteger id;

    public static IdGenerator getInstance() {
        return ourInstance;
    }
    public int getNextAvailableId(){
        return id.incrementAndGet();
    }

    private IdGenerator() {
    }
}

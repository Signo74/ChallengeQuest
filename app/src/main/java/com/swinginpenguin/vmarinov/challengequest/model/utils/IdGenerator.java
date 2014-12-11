package com.swinginpenguin.vmarinov.challengequest.model.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vmarinov on 11/24/2014.
 */
public class IdGenerator {
    private static IdGenerator idGenerator = new IdGenerator();
    private static AtomicInteger id = null;

    // TODO initialize the generator ID property with the biggest one upon app startup
    // TODO find a cleaning algorithm, which should be executed regularly in order to keep the id
    // in check

    public static IdGenerator getInstance() {
        return idGenerator;
    }
    public int getNextAvailableId(){
        if (id != null) {
            return id.incrementAndGet();
        } else {

        }
        return 0;
    }

    private IdGenerator() {
    }
}

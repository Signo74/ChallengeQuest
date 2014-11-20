package com.swinginpenguin.vmarinov.challengequest.multithreading.executor;

import android.database.Cursor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vmarinov on 11/20/2014.
 */
public class dbExecutorService {
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 2;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    LinkedBlockingQueue<Callable<List<Cursor>>> decodeWorkerQueue;

    ExecutorService executor;

    private dbExecutorService() {
        executor = Executors.newCachedThreadPool();
    }
}

package com.swinginpenguin.vmarinov.challengequest.multithreading.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by vmarinov on 11/24/2014.
 */
public class ExecutorServiceProvider {
    private static ExecutorServiceProvider instance = new ExecutorServiceProvider();
    private ExecutorService dbExecutor = null;
    private ExecutorService serverExecutor = null;

    private static int MAX_NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static int INITIAL_NUMBER_OF_CORES = 1;
    private static int NUMBER_OF_SERVICES = 0;
    private static int KEEP_ALIVE_TIME = 1;
    private static TimeUnit KEEP_ALIVE_UNITS = TimeUnit.SECONDS;
    //TODO we should be able to submit Runnable
    private final BlockingQueue<Runnable> mDecodeWorkQueue;
    //TODO implement a map with int as key and the services as items
    //TODO in order to be able to shut them down

    public static ExecutorServiceProvider getInstance() {
        return instance;
    }

    private ExecutorServiceProvider() {
        mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();
        this.dbExecutor = Executors.newSingleThreadExecutor();
        this.serverExecutor = Executors.newSingleThreadExecutor();
    }

    public void shutDownAllServices() {
        //TODO implement shutdown;
    }

    public void shutDownServiceByKey() {
        //TODO implement shutdown;
    }

    public ExecutorService getDbExecutor() {
        if (dbExecutor == null) {
            NUMBER_OF_SERVICES++;
            dbExecutor = Executors.newSingleThreadExecutor();
        }
        return dbExecutor;
    }

    public ExecutorService getServerExecutor() {
        if (serverExecutor == null) {
            NUMBER_OF_SERVICES++;
            this.serverExecutor = Executors.newSingleThreadExecutor();
        }
        return serverExecutor;
    }
}
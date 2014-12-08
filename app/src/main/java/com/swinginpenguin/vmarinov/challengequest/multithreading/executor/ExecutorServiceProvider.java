package com.swinginpenguin.vmarinov.challengequest.multithreading.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by vmarinov on 11/24/2014.
 */
public class ExecutorServiceProvider {
    private static ExecutorServiceProvider instance = new ExecutorServiceProvider();
    private ExecutorService dbExecutor = null;
    private ExecutorService serverExecutor = null;

    private static int NUMBER_OF_IDLE_CORES = 0;
    private static int NUMBER_OF_SERVICES = 0;
    private static int MAX_NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static int NUMBER_OF_AVAILABLE_CORES = MAX_NUMBER_OF_CORES ;
    private static int KEEP_ALIVE_TIME = 1;
    private static TimeUnit KEEP_ALIVE_UNITS = TimeUnit.SECONDS;
    //TODO we should be able to submit Runnable
    private final BlockingQueue<Runnable> dbExecutorQueue;
    private final BlockingQueue<Runnable> serverExecutorQueue;
    //TODO implement a map with int as key and the services as items
    //TODO in order to be able to shut them down
    private Map<String,ExecutorService> executorsMap = new HashMap<String, ExecutorService>();

    public static ExecutorServiceProvider getInstance() {
        return instance;
    }

    private ExecutorServiceProvider() {
        dbExecutorQueue = new LinkedBlockingQueue<Runnable>();
        serverExecutorQueue = new LinkedBlockingQueue<Runnable>();
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
            dbExecutor = new ThreadPoolExecutor(NUMBER_OF_IDLE_CORES, getNumberOfAvailableCores(),
                                            KEEP_ALIVE_TIME, KEEP_ALIVE_UNITS, dbExecutorQueue);
        }
        return dbExecutor;
    }

    public ExecutorService getServerExecutor() {
        if (serverExecutor == null) {
            NUMBER_OF_SERVICES++;
            serverExecutor = new ThreadPoolExecutor(NUMBER_OF_IDLE_CORES, getNumberOfAvailableCores(),
                                                        KEEP_ALIVE_TIME, KEEP_ALIVE_UNITS, serverExecutorQueue);
        }
        return serverExecutor;
    }

    private int getNumberOfAvailableCores() {
        NUMBER_OF_AVAILABLE_CORES = MAX_NUMBER_OF_CORES / NUMBER_OF_SERVICES;
        if (NUMBER_OF_AVAILABLE_CORES <= 0) {
            NUMBER_OF_AVAILABLE_CORES = 1;
        }

        return NUMBER_OF_AVAILABLE_CORES;
    }
}
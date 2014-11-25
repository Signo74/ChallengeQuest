package com.swinginpenguin.vmarinov.challengequest.multithreading.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vmarinov on 11/24/2014.
 */
public class ExecutorServiceProvider {
    private static ExecutorServiceProvider instance = new ExecutorServiceProvider();
    public ExecutorService dbExecutor;
    public ExecutorService serverExecutor;

    public static ExecutorServiceProvider getInstance() {
        return instance;
    }

    private ExecutorServiceProvider() {
        this.dbExecutor = Executors.newSingleThreadExecutor();
        this.serverExecutor = Executors.newSingleThreadExecutor();
    }
}
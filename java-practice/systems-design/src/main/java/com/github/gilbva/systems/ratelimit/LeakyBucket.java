package com.github.gilbva.systems.ratelimit;

import java.util.concurrent.*;

public class LeakyBucket {


    public LeakyBucket(int bucketSize, int tasksPerSec, ExecutorService tasksExecutor) {

    }

    public boolean process(Runnable task) {
        return false;
    }

    public void shutdown() throws InterruptedException {

    }
}

package com.github.gilbva.cspractice.ratelimit;

public class LeakyBucket {
    public LeakyBucket(int bucketSize, int taskRateInSec) {
    }

    public boolean process(Runnable task) {
        return false;
    }
}

package com.github.gilbva.cspractice.ratelimit;

import com.github.gilbva.systems.ratelimit.LeakyBucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LeakyBucketTest {
    @Test
    public void testLeakyBucket() throws InterruptedException {
        var executedCounter = new AtomicInteger();
        var acceptedCounter = new AtomicInteger();
        var failedCounter = new AtomicInteger();
        var executor = Executors.newSingleThreadExecutor();
        var server = new LeakyBucket(10, 10, executor);
        var tasks = createTasks(executedCounter);

        var scheduledExec = Executors.newScheduledThreadPool(1);
        scheduledExec.scheduleAtFixedRate(() -> {
            var task = tasks.removeFirst();
            if(server.process(task)) {
                acceptedCounter.incrementAndGet();
            }
            else {
                failedCounter.incrementAndGet();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);

        scheduledExec.awaitTermination(11, TimeUnit.SECONDS);
        server.shutdown();

        Assertions.assertTrue(115 > acceptedCounter.get(), "too many accepted tasks " + acceptedCounter.get());
        Assertions.assertTrue(115 > executedCounter.get(), "too many executed tasks " + executedCounter.get());
        Assertions.assertTrue(880 < failedCounter.get(), "too few failed tasks " + failedCounter.get());
    }

    private LinkedList<Runnable> createTasks(AtomicInteger counter) {
        var requests = new LinkedList<Runnable>();
        for(int i = 0; i < 1000; i++) {
            requests.add(() -> {
                System.out.println("task executed: " + counter.incrementAndGet());
            });
        }
        return requests;
    }
}

package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.leader.BatchProcess;
import com.github.gilbva.cspractice.leader.LeaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LeaderElectionTest {

    private class ProcessedEvent {
        private String processor;

        private int eventNumber;

        public ProcessedEvent(String processor, int number) {
            this.processor = processor;
            this.eventNumber = number;
        }
    }

    @Test
    public void testLeaseElection() throws InterruptedException {
        Map<String, BatchProcess> processes = new TreeMap<>();

        Queue<Integer> events = createEvents(1000);
        int[] expected = events.stream().mapToInt(x -> x).toArray();
        Arrays.sort(expected);
        Queue<ProcessedEvent> processedEvents = new ConcurrentLinkedQueue<>();

        LeaseService leaseServ = new LeaseService();
        addProcess(processes, leaseServ, "p1", events, processedEvents);
        addProcess(processes, leaseServ, "p2", events, processedEvents);
        addProcess(processes, leaseServ, "p3", events, processedEvents);
        addProcess(processes, leaseServ, "p4", events, processedEvents);

        var executor = Executors.newFixedThreadPool(processes.size());
        for (var process : processes.values()) {
            executor.submit(process);
        }

        waitForNewLeader(leaseServ, null);
        while (!events.isEmpty()) {
            var process = processes.get(leaseServ.getCurrent());
            process.shutdown();
            waitForNewLeader(leaseServ, process.getId());
            process.restart();
            executor.submit(process);
        }

        executor.awaitTermination(10, TimeUnit.SECONDS);
        int[] processed = processedEvents.stream().mapToInt(x -> x.eventNumber).toArray();
        Arrays.sort(processed);
        Assertions.assertEquals(expected, processed);
    }

    private Queue<Integer> createEvents(int count) {
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        for(int i = 0; i < count; i++) {
            queue.add(i);
        }
        return queue;
    }

    private void addProcess(Map<String, BatchProcess> processes,
                            LeaseService leaseServ, String id,
                            Queue<Integer> events, Queue<ProcessedEvent> processedEvents) {
        processes.put(id, new BatchProcess(id, leaseServ, () -> processData(id, events, processedEvents)));
    }

    public void processData(String procId, Queue<Integer> events, Queue<ProcessedEvent> processedEvents) {
        if(events.isEmpty()) {
            processedEvents.offer(new ProcessedEvent(procId, events.poll()));
            TestUtils.sleep(10);
        }
    }

    private void waitForNewLeader(LeaseService leaseServ, String current) {
        while ( leaseServ.getCurrent() == null
                || (current != null && current.equals(leaseServ.getCurrent())) ) {
            TestUtils.sleep(1000);
        }
    }
}

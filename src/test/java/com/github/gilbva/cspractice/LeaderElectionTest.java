package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.leader.BatchProcess;
import com.github.gilbva.cspractice.leader.LeaseService;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LeaderElectionTest {

    @Test
    public void testLeaseElection() throws InterruptedException {
        Map<String, BatchProcess> processes = new TreeMap<>();

        LeaseService leaseServ = new LeaseService();
        processes.put("p1", new BatchProcess("p1", leaseServ));
        processes.put("p2", new BatchProcess("p2", leaseServ));
        processes.put("p3", new BatchProcess("p3", leaseServ));
        processes.put("p4", new BatchProcess("p4", leaseServ));

        var executor = Executors.newFixedThreadPool(processes.size());
        for (var process : processes.values()) {
            executor.submit(process);
        }

        waitForNewLeader(leaseServ, null);
        for(int i = 0; i < 10; i++) {
            var process = processes.get(leaseServ.getCurrent());
            process.shutdown();
            waitForNewLeader(leaseServ, process.getId());
            process.restart();
            executor.submit(process);
        }

        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    private void waitForNewLeader(LeaseService leaseServ, String current) {
        while ( leaseServ.getCurrent() == null
                || (current != null && current.equals(leaseServ.getCurrent())) ) {
            try {
                Thread.sleep(1000);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

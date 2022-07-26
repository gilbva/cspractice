package com.github.gilbva.cspractice.cloud.ratelimit;

import com.github.gilbva.cspractice.TestUtils;
import com.github.gilbva.cspractice.cloud.ratelimit.WindowCounter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WindowCounterTest {

    private static final class RateRequest {
        private int id;

        String key;

        LocalDateTime time;

        boolean allowed;

        public RateRequest(int id, String key) {
            this.id = id;
            this.key = key;
        }

        public void allowed() {
            allowed = true;
            time = LocalDateTime.now();
        }

        public void denied() {
            allowed = false;
            time = LocalDateTime.now();
        }
    }

    @Test
    public void testWindowCounter() {
        var counter = new WindowCounter(10);
        var requests = createRequests(1000);
        processRequests(requests,  counter, 10);

        var format = DateTimeFormatter.ofPattern("HHmmss");
        Assertions.assertTrue(requests.stream().anyMatch(x -> x.allowed));
        var map= requests.stream()
                .filter(x -> x.allowed)
                .collect(Collectors.groupingBy(x -> x.key + x.time.truncatedTo(ChronoUnit.SECONDS).format(format)));
        for (var value : map.values()) {
            Assertions.assertTrue(value.size() <= counter.getMaxCount());
        }
    }

    private void processRequests(List<RateRequest> requests, WindowCounter counter, int delay) {
        requests.parallelStream()
                .forEach(request -> {
                    if(counter.isAllow(request.key)) {
                        System.out.println(request.id + ": request approved");
                        request.allowed();
                    } else {
                        System.out.println(request.id + ": request denied");
                        request.denied();
                    }
                    TestUtils.sleep(delay);
                });
    }

    private List<RateRequest> createRequests(int count) {
        String[] users = new String[] { "user1", "user2", "user3" };
        List<RateRequest> requests = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            requests.add(new RateRequest(i, users[i % users.length]));
        }
        return requests;
    }
}

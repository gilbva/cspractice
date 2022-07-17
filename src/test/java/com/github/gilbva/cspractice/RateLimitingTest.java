package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.ratelimit.TokenBucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RateLimitingTest {

    @FunctionalInterface
    private interface RateRequest {
        double execute();
    }

    private static final class User {
        private String id;

        private int requests;

        private LocalDateTime startTime;

        public User(String id, LocalDateTime startTime) {
            this.id = id;
            this.startTime = startTime;
        }
    }

    @TestFactory
    Collection<DynamicTest> testTokenBucket() {
        Collection<DynamicTest> tests = new ArrayList<>();
        for(int i = 1; i <= 10; i+=3) {
            for(int j = 1; j <= 10; j+=3) {
                final int maxTokens = i;
                final int refill = j;
                tests.add(DynamicTest.dynamicTest("token bucket tokens: " + maxTokens + ", " + refill + " secs", () -> testTokenBucket(maxTokens, refill)));
            }
        }
        return tests;
    }

    public void testTokenBucket(int maxTokens, int refill) {
        var users = Stream.of("user1", "user2", "user3")
                .map(id -> new User(id, LocalDateTime.now().minusSeconds(1)))
                .collect(Collectors.toList());
        var server = new TokenBucket(maxTokens, refill);
        var rates = createRequests(server, users)
                .parallelStream()
                .map(RateRequest::execute)
                .collect(Collectors.toList());

        for(var rate : rates) {
            Assertions.assertTrue(0d < rate && rate <= maxTokens, "rate should be between 0 and " + maxTokens + ", but was " + rate);
        }
    }

    public List<RateRequest> createRequests(TokenBucket server, List<User> users) {
        var requests = new ArrayList<RateRequest>();
        for(int i = 0; i < 1000; i++) {
            for(var user : users) {
                requests.add(() -> testTokenBucket(server, user));
            }
        }
        return requests;
    }

    public double testTokenBucket(TokenBucket server, User user) {
        if (server.isAllow(user.id)) {
            user.requests++;

            long seconds = ChronoUnit.SECONDS.between(user.startTime, LocalDateTime.now());
            long rate = seconds > 0 ? user.requests / seconds : user.requests;
            System.out.println(user.id + "> count: " + user.requests + " secs: " + seconds + " rate: " + rate);
        }

        long seconds = ChronoUnit.SECONDS.between(user.startTime, LocalDateTime.now());
        double rate = seconds > 0 ? user.requests / (double)seconds : user.requests;
        if(rate > server.getMaxTokens()) {
            System.out.println("ERR: " + user.id + "> count: " + user.requests + " secs: " + seconds + " rate: " + rate);
        }
        TestUtils.sleep(6);
        return rate;
    }
}

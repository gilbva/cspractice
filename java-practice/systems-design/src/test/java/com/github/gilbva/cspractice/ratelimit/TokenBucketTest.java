package com.github.gilbva.cspractice.ratelimit;

import com.github.gilbva.cspractice.TestUtils;
import com.github.gilbva.systems.ratelimit.TokenBucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenBucketTest {

    @FunctionalInterface
    private interface RateRequest {
        double execute();
    }

    private static final class User {
        private String id;

        private AtomicInteger requests;

        private LocalDateTime startTime;

        public User(String id, LocalDateTime startTime) {
            this.id = id;
            this.startTime = startTime;
            this.requests = new AtomicInteger();
            this.requests.set(0);
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
                .map(id -> new User(id, LocalDateTime.now().minusSeconds(refill)))
                .collect(Collectors.toList());
        var server = new TokenBucket(maxTokens, refill);
        var rates = createRequests(server, users)
                .parallelStream()
                .map(RateRequest::execute)
                .collect(Collectors.toList());

        for(var user : users) {
            Assertions.assertTrue(user.requests.get() > 0, "no requests were accepted for user: " + user.id);
        }

        double serverRate = (double) server.getRefillTokens() / server.getRefillTime();
        for(var rate : rates) {
            Assertions.assertTrue(rate <= serverRate, "rate should be between 0 and " + serverRate + ", but was " + rate);
        }
    }

    private double testTokenBucket(TokenBucket server, User user) {
        int requests = user.requests.get();
        double serverRate = (double) server.getRefillTokens() / server.getRefillTime();
        if (server.isAllow(user.id)) {
            requests = user.requests.incrementAndGet();

            long seconds = ChronoUnit.SECONDS.between(user.startTime, LocalDateTime.now());
            double rate = seconds > 0 ? requests / (double)seconds : requests;
            System.out.println(user.id + "> count: " + requests + " secs: " + seconds + " rate(tokens/secs): " + rate + " max rate(tokens/secs): " + serverRate);
        }

        long seconds = ChronoUnit.SECONDS.between(user.startTime, LocalDateTime.now());
        double rate = seconds > 0 ? requests / (double)seconds : requests;
        if(rate > serverRate) {
            System.out.println("ERR: " + user.id + "> count: " + requests + " secs: " + seconds + " rate(tokens/secs): " + rate + " max rate(tokens/secs): " + serverRate);
        }
        TestUtils.sleep(6);
        return rate;
    }

    private List<RateRequest> createRequests(TokenBucket server, List<User> users) {
        var requests = new ArrayList<RateRequest>();
        for(int i = 0; i < 1000; i++) {
            for(var user : users) {
                requests.add(() -> testTokenBucket(server, user));
            }
        }
        return requests;
    }
}

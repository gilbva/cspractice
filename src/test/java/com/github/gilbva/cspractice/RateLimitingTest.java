package com.github.gilbva.cspractice;

import com.github.gilbva.cspractice.ratelimit.TokenBucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RateLimitingTest {

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
        var tokenBucket = new TokenBucket(maxTokens, refill);

        String[] keys = new String[]{ "user1", "user2", "user3" };
        Map<String, Map<LocalDateTime, Integer>> allowedRequest = new HashMap<>();
        for(String key : keys) {
            allowedRequest.put(key, new HashMap<>());
        }

        for(int i = 0; i < 1000; i++) {
            for(String key : keys) {
                var current = allowedRequest.get(key);
                var time = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
                if (tokenBucket.isAllow(key)) {
                    int count = current.getOrDefault(time, 0) + 1;
                    current.put(time, count);

                    Assertions.assertTrue(count <= maxTokens, "count should be less than two, but was " + count);
                }
                else {
                    Assertions.assertTrue(current.containsKey(time), "no request was accepted");
                }

                TestUtils.sleep(4);
            }
        }
    }
}

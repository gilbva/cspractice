package com.github.gilbva.cspractice.dataservers;

import com.github.gilbva.cspractice.TestUtils;
import com.github.gilbva.systems.dataservers.RedisCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class RedisCacheTest {
    @Test
    public void testCache() throws Exception {
        try(var redisCache = new RedisCache("redis", 6379)) {
            Map<String, String> map = createKeys(1000);

            clearOneByOne(redisCache);
            addAll(redisCache, map, 5);

            redisCache.removeAll();
            Assertions.assertTrue(redisCache.allKeys().isEmpty());

            addAll(redisCache, map, 1);
            TestUtils.sleep(1100);
            for (var kp : map.entrySet()) {
                System.out.println("get: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()));
                Assertions.assertNull(redisCache.get(kp.getKey()), kp.getKey() + " must have expired");
            }

            addAll(redisCache, map, 10);
            for (var kp : map.entrySet()) {
                System.out.println("get: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()) + " ttl: " + redisCache.expirationTime(kp.getKey()));
                Assertions.assertEquals(redisCache.getLength(kp.getKey()), kp.getValue().length(), kp.getKey() + " must be of correct length");
                Assertions.assertTrue(redisCache.expirationTime(kp.getKey()) > 8, "expiration time should be at least 8 sec");
                Assertions.assertEquals(redisCache.get(kp.getKey()), kp.getValue(), kp.getKey() + " must be present");
            }

            for (var kp : map.entrySet()) {
                Assertions.assertFalse(redisCache.putIfAbsent(kp.getKey(), "other"));
                Assertions.assertTrue(redisCache.putIfAbsent(kp.getKey() + "-1", kp.getValue()));
            }

            for (var kp : map.entrySet()) {
                System.out.println("deleting: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()));
                redisCache.remove(kp.getKey());
                redisCache.remove(kp.getKey() + "-1");
            }

            for (var kp : map.entrySet()) {
                System.out.println("get: " + kp.getKey() + " -> " + redisCache.get(kp.getKey()));
                Assertions.assertNull(redisCache.get(kp.getKey()), kp.getKey() + " must not exists");
            }

            addAll(redisCache, map, 100);

            String[] expected = map.keySet().toArray(new String[0]);
            String[] allKeys = redisCache.allKeys().toArray(new String[0]);

            Arrays.sort(expected);
            Arrays.sort(allKeys);

            Assertions.assertArrayEquals(expected, allKeys);

            Assertions.assertEquals(1, redisCache.increment("num1"));
            Assertions.assertEquals(2, redisCache.increment("num1"));
            Assertions.assertEquals(3, redisCache.increment("num1"));
            Assertions.assertEquals(2, redisCache.decrement("num1"));
            Assertions.assertEquals(1, redisCache.decrement("num1"));
            Assertions.assertEquals(0, redisCache.decrement("num1"));

            Assertions.assertEquals(5, redisCache.increment("num2", 5));
            Assertions.assertEquals(9, redisCache.increment("num2", 4));
            Assertions.assertEquals(12, redisCache.increment("num2", 3));
            Assertions.assertEquals(0, redisCache.decrement("num2", 12));
            Assertions.assertEquals(-1, redisCache.decrement("num2", 1));
            Assertions.assertEquals(-4, redisCache.decrement("num2", 3));
        }
    }

    private Map<String, String> createKeys(int count) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < count; i++) {
            map.put("test-key-" + UUID.randomUUID().toString().substring(0, 6), "value" + i);
        }
        return map;
    }

    private void clearOneByOne(RedisCache redisCache) {
        for (var key : redisCache.allKeys()) {
            redisCache.remove(key);
        }
        Assertions.assertTrue(redisCache.allKeys().isEmpty());
    }

    private void addAll(RedisCache redisCache, Map<String, String> map, int expirationInSec) {
        for (var kp : map.entrySet()) {
            redisCache.put(kp.getKey(), kp.getValue(), expirationInSec);
        }
        Assertions.assertFalse(redisCache.allKeys().isEmpty());
    }
}
